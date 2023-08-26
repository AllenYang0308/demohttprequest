package org.example;

import java.io.*;
import org.example.connection.ApisConnection;

public class Main {
    public static void main(String[] args) throws IOException {
        ApisConnection apisConnection = new ApisConnection(args);
        apisConnection.properties.setUsername("<username>");
        apisConnection.properties.setPassword("<password>");
        apisConnection.setRequestAuthenticate("BasicAuthentication");
        apisConnection.setRequestProperty("Content-Type", "application/json");
        apisConnection.GetApisResult();
    }
}