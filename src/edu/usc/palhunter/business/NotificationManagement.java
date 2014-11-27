/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.palhunter.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Luan
 */
public class NotificationManagement {
    public static final String NEW_NEAREST_NEIGHBOR = "You have new neareast neighbor ";
    
    public static String sendNotif(int userId, String content){
        return null;
    }
    
    public static String sendNotif(String regId, String content) {
        String result = "";
        HashMap<String, Object> message = new HashMap<String, Object>();
        // Id-List to send
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(regId);
        message.put("registration_ids", ids);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("content", content);
        message.put("data", data);

        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL("https://android.googleapis.com/gcm/send");
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.addRequestProperty("Authorization", "key=" + "AIzaSyAg4wpwDTMnJE7t4FEBux_upfPeFBskTVM");

            httpConnection.setDoOutput(true);
        } catch (Exception e) {
//			logger.error(e);
        }
        if (httpConnection == null) {
            return "error httpconnection";
        }

        try {
            OutputStream output = httpConnection.getOutputStream();
            String dataJSON = "";
            ObjectMapper mapper
                    = new ObjectMapper();
            dataJSON = mapper.writeValueAsString(message);

            output.write(dataJSON.getBytes());
            output.close();
            System.out.println("finish push");
            int responseCode = httpConnection.getResponseCode();
            System.out.println(responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            result = response.toString();
//                        result = output.toString();
//                        System.out.println(result);
            return result;
        } catch (Exception e) {
//			logger.error(e);
            return "error reading response " + e.toString();
        }

    }

}

