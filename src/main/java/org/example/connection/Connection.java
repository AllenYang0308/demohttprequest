package org.example.connection;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.example.authentication.Authenticate;

public class Connection {

    private HttpURLConnection connection;
    private int responseCode;

    // public Connection() {}
    public Connection(String targetUrl, Authenticate authenticate, String method) throws ProtocolException {
        try {
            System.out.println("Not need certificate");
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "Basic " + authenticate.getBasicAuthenticationCredential());
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection(String targetUrl, Authenticate authenticate, String method, String certPath) throws IOException {
        try {
            System.out.println("Need certificate");

            new Certification(certPath);

            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "Basic " + authenticate.getBasicAuthenticationCredential());
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int GetResponseCode() {
        return this.responseCode;
    }

    public void PrintResponse() throws IOException {
        if (this.responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("Http request Failed wiht response code: " + this.responseCode);

        }
    }

    public void ClsoeConnection() {
        this.connection.disconnect();
    }
}
