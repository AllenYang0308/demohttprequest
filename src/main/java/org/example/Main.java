package org.example;

import java.io.*;
import org.example.connection.ApisConnection;

public class Main {
    public static void main(String[] args) throws IOException {
        ApisConnection apisConnection = new ApisConnection(args);
        Settings settings = new Settings("./settings.yaml");
        UserSettings userSettings = settings.getUserSettings();
        apisConnection.properties.setUsername(userSettings.userName);
        apisConnection.properties.setPassword(userSettings.passWord);
        apisConnection.setRequestAuthenticate("BasicAuthentication");
        apisConnection.setRequestProperty("Content-Type", "application/json");
        apisConnection.GetApisResult();
    }
}