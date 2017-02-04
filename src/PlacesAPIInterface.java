import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alistair on 27/01/2017.
 */
public abstract class PlacesAPIInterface {

    abstract Location[] lookuplocation (String name);

    static final String APIKey = Keys.placesAPIKey;

    static final String urlPref =  "https://maps.googleapis.com/maps/api/place/autocomplete/json?key="+APIKey+"&types=(cities)&input=";

    abstract String composeRequest(String name);

    static void makeRequest(String name) throws Exception {
        URL url = new URL((urlPref+name));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            //System.out.println(line);
            response.append(line);
            response.append('\n');
        }
        rd.close();
        System.out.println(response.toString());


    }

}
