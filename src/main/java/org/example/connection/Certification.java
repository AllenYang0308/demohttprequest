package org.example.connection;

import lombok.Getter;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;

public class Certification {

    @Getter
    private X509TrustManager[] trustManagers;

    @Getter
    TrustManagerFactory trustManagerFactory;

    @Getter
    KeyStore keyStore;

    public Certification(String certPath) {
        initX509TrustManager();
        setTrustManagerFactory(certPath);
    }

    private void initX509TrustManager() {
        trustManagers = new X509TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] cert, String authType) {}
                    public void checkServerTrusted(X509Certificate[] cert, String authType) {}
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
}