package org.example;

import org.example.authentication.Authenticate;
import org.example.authentication.Properties;
import org.example.connection.Certification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            String apiURL = args[1];
            String method = args[0];
            String postData = args[2];
            if (args.length>3) {
                new Certification(args[3]);
            }
            URL url = new URL(apiURL);
            Properties properties = new Properties();
            properties.setUsername("<username>");
            properties.setPassword("<password>");
            Authenticate authenticate = new Authenticate(properties);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Authorization", authenticate.getBasicAuthenticationCredential());
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            boolean isMatch = postData.equalsIgnoreCase("");
            if (isMatch) {
                System.out.println("No parameters.");
            } else {
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(postData);
                outputStream.flush();
                outputStream.close();
            }

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            System.out.println("Response: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}