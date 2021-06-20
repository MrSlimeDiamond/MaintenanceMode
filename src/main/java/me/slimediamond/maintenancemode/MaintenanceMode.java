package me.slimediamond.maintenancemode;

import lombok.SneakyThrows;
import me.slimediamond.maintenancemode.util.Log;

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class MaintenanceMode {
    public static Properties prop;
    private static MaintenanceMode instance;
    public static Scanner confRead;
    private static FileWriter confWrite;
    public static ServerSocket serverSocket;

    static boolean running = true;
    public static void main(String[] args) throws IOException {
        Log.info("Starting...");
        /*
        try {
            File config = new File("config.conf");
            if (!config.exists()) {
                Log.info("Config file does not exist, creating...");
                config.createNewFile();
                confWrite = new FileWriter(config);
                confWrite.write("# MaintenanceMode Config File\n");
                confWrite.write("motd: We are down for maintenance!\n");
                confWrite.write("kickmsg: We are down for maintenance!\n");
                confWrite.write("address: 127.0.0.1\n");
                confWrite.write("port: 25565\n");
                confWrite.close();
            }
            confRead = new Scanner(config);
            //new Config();
            Log.info("MOTD: "+Config.getOption("motd"));
            Log.info("Kick message: "+Config.getOption("kickmsg"));
            /*
            Log.info("Config file:");
            while (confRead.hasNextLine()) {
                Log.info(confRead.nextLine());
            }

        } catch(IOException e) {
            Log.error("Could not create config file, stack trace:");
            e.printStackTrace();
        }*/

        new LoadConfig();

        new Server(serverSocket);
    }
    public static MaintenanceMode getInstance() {
        return instance;
    }
}
