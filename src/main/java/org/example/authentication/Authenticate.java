package org.example.authentication;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Authenticate {

    private Properties properties;
    public Authenticate(Properties properties) {
        this.properties = properties;
    }

    public String getBasicAuthenticationCredential() throws UnsupportedEncodingException {
        String username = this.properties.username;
        String password = this.properties.password;
        String str_credentials = username + ":" + password;
        String credentials = Base64.getEncoder().encodeToString(
                str_credentials.getBytes("UTF-8")
        );
        return "Basic " + credentials;
    }
}
