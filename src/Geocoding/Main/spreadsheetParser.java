package Geocoding.Main;

import Geocoding.Exceptions.NoArticleException;
import Geocoding.NER.NER;
import Geocoding.NER.NEROutput;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by alistair on 03/02/2017.
 */
class spreadsheetParser {

     static ArrayList<Example> parseFile() {
         ArrayList<Example> result = new ArrayList();
         ArrayList<Integer> badLines = new ArrayList<>();
         try {
            FileInputStream file = new FileInputStream(new File("/Users/alistair/Documents/DissResources/data.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); //skip headings row
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                if (row.getRowNum() % 500 == 0) {
                    System.out.printf("Parsed %d rows\n", row.getRowNum());
                }
                boolean fireRow;
                try {
                    fireRow = row.getCell(19).getStringCellValue().equals("fire");
                }
                catch (IllegalStateException e) {
                    fireRow = false;
                }
                if (fireRow) {
                    try {
                        Example example = parseRow(row);
                        if (example.articles.size()>0 && example.labels.location.sufficient) { //filter rows with no articles (there are around 4)
                            result.add(example);
                        }
                    }
                    catch (NoArticleException e){
                        badLines.add(e.rowNumber);
                    }
                }
            }
            file.close();
        }
        catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        System.out.println(result.size());
        return result;
    }

    private static Example parseRow(Row row) throws Exception {
        Example result = new Example();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            setExampleField(result,cell);
        }
        return result;
    }

    private static void setExampleField(Example e, Cell c) throws Exception {
        switch (c.getColumnIndex()) {
            case 0 :
                e.id = c.getStringCellValue();
                break;
            case 2 :
                e.title = c.getStringCellValue();
                break;
            case 3:
                e.labels.eventSentence = c.getStringCellValue();
                break;
            case 4:
                String originalLocation = c.getStringCellValue();
                String fixed = originalLocation.replace(" Rd", " Road").replace(" St", " Street").replace(" Ave", " Avenue").replace(" Ln", " Lane");
                e.labels.bestGeopoint.name = fixed;
                e.labels.location = new Location(fixed);
                break;
            case 6:
                e.labels.bestGeopoint.houseNumber = c.getBooleanCellValue();
                break;
            case 7:
                parseGeopoint(c.getStringCellValue(),e.labels.bestGeopoint);
            case 8:
                e.labels.bestGeopoint.country = c.getStringCellValue();
                break;
            case 12:
                try {
                    String articleJSON = c.getStringCellValue();
                    ArrayList<String> strings =  extractArticles(articleJSON);
                    ArrayList tokens = new ArrayList();
                    ArrayList locations = new ArrayList();
                    for (String s: strings) {
                        NEROutput output = NER.getLocations(s);
                        tokens.add(output.tokens);
                        locations.addAll(output.entities);
                    }
                    e.articles = tokens;
                    e.entities = locations;
                    if (e.articles.size() > 0 && e.labels.bestGeopoint.name != null) {
                        Token.checkAddress(e.articles.get(0), e.labels.bestGeopoint.name, e.labels.location);
                        Token.checkPosition(e.articles.get(0));
                    }
                    break;
                }
                catch (IllegalStateException ex) {
                    throw new NoArticleException(c.getRowIndex());
                }
            default:
                break;
        }
    }

    private static ArrayList<String> extractArticles(String s) {
        String[] splitStrings = s.split("\\{");
        ArrayList<String> result = new ArrayList<>();
        for (String s2: splitStrings) {
            if ((s2.startsWith("\\\"body") || (s2.startsWith("\\body")))) {
                String s3 = s2.substring(11);
                String s4 = s3.split("\"url")[0];
                result.add(s4);
            }
        }
        return result;
    }

    private static void parseGeopoint(String s, Geopoint geopoint) { //input is a string of the form "(double,double)"
        String noBrackets = s.substring(1,s.length()-2);
        String[] coords = noBrackets.split(",");
        Double latitude = Double.parseDouble(coords[0]);
        Double longitude = Double.parseDouble(coords[1]);
        geopoint.setGeopoint(longitude,latitude);
    }

}

