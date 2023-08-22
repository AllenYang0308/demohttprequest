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
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(
                credentials.getBytes("UTF-8")
        );
    }
}
