package org.example;

import org.example.authentication.Authenticate;
import org.example.authentication.Properties;
import org.example.connection.Connection;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        properties.setUsername("<username>");
        properties.setPassword("<password>");

        Authenticate authenticate = new Authenticate(properties);

        Connection conn = new Connection(
                "<target url>",
                authenticate,
                "GET",
                "<certificate path>"
        );
        System.out.println(conn.GetResponseCode());
        conn.PrintResponse();
        conn.ClsoeConnection();
    }
}