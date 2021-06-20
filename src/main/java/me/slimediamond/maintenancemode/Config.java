package me.slimediamond.maintenancemode;

import me.slimediamond.maintenancemode.util.Log;

import java.io.*;
import java.util.Date;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Config {
    private Properties prop;
    public Config() throws IOException {
        File propFile = new File("./server.properties");
        if (!propFile.exists()) {
            Log.info("Properties file does not exist, creating...");
            try (OutputStream output = new FileOutputStream("./server.properties")) {

                prop = new Properties();

                prop.setProperty("motd", "My server is under maintenance");
                prop.setProperty("kickmsg", "Hello, my server is down for maintenance. Come back soon!");
                prop.setProperty("address", "127.0.0.1");
                prop.setProperty("port", "25565");
                prop.setProperty("version", "Maintenance");

                prop.store(output, null);

                 new LoadConfig();
            } catch (IOException e) {
                Log.error(e.getStackTrace());
            }
        }
    }
}