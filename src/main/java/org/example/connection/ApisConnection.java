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

@Getter
public class ApisConnection {

    private final String postData;

    public Properties properties;

    private final HttpURLConnection connection;

    private Authenticate authenticate;

    public ApisConnection(ConnectionProperty args) throws IOException {
        String apiURL = args.apiUrl;
        String method = args.method;
        postData = args.postData;
        boolean isMatch = args.certFile.equalsIgnoreCase("");
        if (!isMatch) {
            new Certification(args.certFile);
        }
        URL url = new URL(apiURL);
        properties = new Properties();
        connection = (HttpURLConnection) url.openConnection();
        boolean patch = method.equalsIgnoreCase("PATCH");
        if (patch) {
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            connection.setRequestMethod("POST");
        } else {
            connection.setRequestMethod(method);
        }
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

    public String GetApisResult() throws IOException {
        String rsp = "";

        boolean isMatch = this.postData.equalsIgnoreCase("");
        if (!isMatch) {
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
            rsp = response.toString();
        } else {
            rsp = "{\"status\": \"Resource not Found\"}";
        }
        return rsp;
    }
}