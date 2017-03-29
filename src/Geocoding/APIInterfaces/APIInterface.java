package Geocoding.APIInterfaces;

import Geocoding.Keys;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alistair on 04/02/2017.
 */
abstract class APIInterface {

    static final String APIKey = Keys.placesAPIKey2;

     static <T> T responseFromJSON(String json, Class<T> c){
        Gson gson = new Gson();
        return gson.fromJson(json,c);
    }

    static String makeRequest(String input, String urlString) throws Exception {
        URL url = new URL((urlString+input));
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
        return response.toString();
    }

}
