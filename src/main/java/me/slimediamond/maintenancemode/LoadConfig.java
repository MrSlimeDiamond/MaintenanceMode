package me.slimediamond.maintenancemode;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static me.slimediamond.maintenancemode.MaintenanceMode.prop;

public class LoadConfig {
    public LoadConfig() throws IOException {
            try {
                FileReader reader = new FileReader("server.properties");
                prop = new Properties();
                prop.load(reader);
            } catch (IOException e) {
                new Config();
            }
    }
}
