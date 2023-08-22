package org.example;

import lombok.Getter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;

public class HttpGetWithAuthentication {

    @Getter
    private X509TrustManager[] trustManagers;

    @Getter
    TrustManagerFactory trustManagerFactory;

    @Getter
    KeyStore keyStore;

    public HttpGetWithAuthentication(String certPath) {
        initX509TrustManager();
        setTrustManagerFactory(certPath);
    }

    private void initX509TrustManager() {
        trustManagers = new X509TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
    }

    private void setTrustManagerFactory(String certPath) {
       try {
           SSLContext sslContext = SSLContext.getInstance("TLS");

           sslContext.init(null, trustManagers, new java.security.SecureRandom());
           HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
           FileInputStream certificateInputStream = new FileInputStream(certPath);

           CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
           Certificate certificate = certificateFactory.generateCertificate(certificateInputStream);

           KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
           keyStore.load(null, null);
           keyStore.setCertificateEntry("example", certificate);

           trustManagerFactory = TrustManagerFactory.getInstance(
                   TrustManagerFactory.getDefaultAlgorithm()
           );

           trustManagerFactory.init(keyStore);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public void GetHttpResponse(String targetUrl) {
        try {

           URL url = new URL(targetUrl);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");

           String username = "musasi.yang@gmail.com";
           String password = "3klalattjbu2j2mkd62nvdbhvd4vy4x5ejikcgukxglhobd2bieq";
           String credentials = username + ":" + password;
           String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes("UTF-8"));
           connection.setRequestProperty("Authorization", "Basic " + base64Credentials);
           int responseCode = connection.getResponseCode();

           if (responseCode == HttpURLConnection.HTTP_OK) {
               BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
               String inputLine;
               StringBuffer response = new StringBuffer();

               while ((inputLine = in.readLine()) != null) {
                   response.append(inputLine);
               }
               in.close();

               System.out.println("Response: " + response.toString());
           } else {
               System.out.println("HTTP Request Failed with response code: " + responseCode);
           }

           connection.disconnect();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}