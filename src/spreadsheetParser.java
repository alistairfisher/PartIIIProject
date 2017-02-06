
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
public class spreadsheetParser {

     static ArrayList<Example> parseFile() {
         ArrayList<Example> result = new ArrayList();
         try {
            FileInputStream file = new FileInputStream(new File("/Users/alistair/Documents/DissResources/data.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); //skip headings row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    Example example = parseRow(row);
                    result.add(example);
                }
                catch (NoArticleException e){}
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

    static Example parseRow(Row row) throws NoArticleException {
        Example result = new Example();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            setExampleField(result,cell);
        }
        return result;
    }

    static void setExampleField(Example e, Cell c) throws NoArticleException {
        switch (c.getColumnIndex()) {
            case 0 :
                e.id = c.getStringCellValue();
                break;
            case 1 :
                e.docid = c.getStringCellValue();
                break;
            case 2 :
                e.title = c.getStringCellValue();
                break;
            case 3:
                e.labels.eventSentence = c.getStringCellValue();
                break;
            case 4:
                e.labels.bestGeolocation = c.getStringCellValue();
                break;
            case 7:
                e.labels.bestGeopoint = parseGeopoint(c.getStringCellValue());
            case 8:
                e.labels.country = c.getStringCellValue();
                break;
            case 12:
                try {
                    e.article = c.getStringCellValue();
                }
                catch (IllegalStateException ex) {
                    throw new NoArticleException(c.getRowIndex());
                }
                finally {
                    break;
                }
            default:
                break;
        }
    }

    static Geopoint parseGeopoint(String s) { //input is a string of the form "(double,double)"
        String noBrackets = s.substring(1,s.length()-2);
        String[] coords = noBrackets.split(",");
        Double longitude = Double.parseDouble(coords[0]);
        Double latitude = Double.parseDouble(coords[1]);
        return new Geopoint(longitude,latitude);
    }

}

