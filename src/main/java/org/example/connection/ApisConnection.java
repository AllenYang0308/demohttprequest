package org.example.connection;

import lombok.Getter;
import org.example.authentication.Properties;
import org.example.authentication.Authenticate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApisConnection {

    @Getter
    private String postData;

    @Getter
    public Properties properties;

    @Getter
    private HttpURLConnection connection;

    @Getter
    private Authenticate authenticate;

    public ApisConnection(String[] args) throws IOException {
        String apiURL = args[1];
        String method = args[0];
        postData = args[2];
        if (args.length>3) {
            new Certification(args[3]);
        }
        URL url = new URL(apiURL);
        properties = new Properties();
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    public void setRequestProperty(String rtype, String rvalue) {
        this.connection.setRequestProperty(rtype, rvalue);
    }

    public void setRequestAuthenticate(String authType) throws UnsupportedEncodingException {
        switch (authType) {
            case "BasicAuthentication" -> {
                authenticate = new Authenticate(this.properties);
                this.setRequestProperty("Authorization", authenticate.getBasicAuthenticationCredential());
            }
        }
    }

    public void GetApisResult() throws IOException {
        boolean isMatch = postData.equalsIgnoreCase("");
        if (isMatch) {
            System.out.println("No parameter");
        } else {
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(postData);
            outputStream.flush();
            outputStream.close();
        }
        int responseCode = this.connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("Resource Not Found");
        }
    }
}