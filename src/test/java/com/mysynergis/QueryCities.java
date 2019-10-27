package com.mysynergis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;

public class QueryCities {

    public static void main(String[] args) throws Exception {

        URL url = new URL(
                "https://w-lap-fleischer.synergis.intern/server/rest/services/world_citylabel/MapServer/74/query?where=1=1&outFields=OBJECTID&returnGeometry=true&f=json&resultRecordCount=30000");
        URLConnection connection = url.openConnection();

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }

        JSONObject responseJo = new JSONObject(responseBuilder.toString());
        JSONArray featuresJa = responseJo.getJSONArray("features");

        for (int i = 0; i < featuresJa.length(); i++) {

            JSONObject featureJo = featuresJa.getJSONObject(i);
            int cityObjectId = featureJo.getJSONObject("attributes").getInt("OBJECTID");
            double xCoord = featureJo.getJSONObject("geometry").getDouble("x");
            double yCoord = featureJo.getJSONObject("geometry").getDouble("y");

            double scale = 5000000;
            double dpi = 72;
            double mmPerInch = 25.4;
            double mmPerPixel = mmPerInch / dpi;
            double mPerPixel = mmPerPixel / 1000 * scale;

            double offset = 25000;
            double xOff = offset / mPerPixel - 5;
            double yOff = offset / mPerPixel + 1;

            String featureJson = String.format(Locale.US, "{\"attributes\":{\"FEATUREID\":%s,\"XOffset\":%f,\"YOffset\":%f},\"geometry\":{\"x\":%f,\"y\":%f}}", cityObjectId, xOff, yOff,
                    xCoord + offset, yCoord + offset);

            System.out.println(featureJson + ",");

            //break;

        }

    }

}
