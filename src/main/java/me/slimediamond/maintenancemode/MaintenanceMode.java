package me.slimediamond.maintenancemode;

import lombok.SneakyThrows;
import me.slimediamond.maintenancemode.util.Log;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class MaintenanceMode {
    private static MaintenanceMode instance;
    public static Scanner confRead;
    private static FileWriter confWrite;
    public static ServerSocket serverSocket;
    static int port = 25565;
    static InetAddress addr;

    static { /* sigh lol */
        try {
            addr = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            Log.error("Could not bind to "+addr.toString()+" stack trace:");
            e.printStackTrace();
        }
    }

    static boolean running = true;
    public static void main(String[] args) throws IOException {
        Log.info("Starting...");
        Log.info("Initializing shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @SneakyThrows
            @Override
            public void run(){
                Log.info("Shutting down...");
                serverSocket.close();
                Log.info("Thank you and goodbye.");
            }
        });
        try {
            serverSocket = new ServerSocket(port, 1, addr);
        } catch(BindException e) {
            Log.error("Could not bind to port "+port);
            Log.error("Maybe there is already a server running on that port?");
            System.exit(1);
            return;
        }
        try {
            File config = new File("config.conf");
            if (!config.exists()) {
                Log.info("Config file does not exist, creating...");
                config.createNewFile();
                confWrite = new FileWriter(config);
                confWrite.write("# MaintenanceMode Config File\n");
                confWrite.write("motd: We are down for maintenance!\n");
                confWrite.write("kickmsg: We are down for maintenance!\n");
                confWrite.close();
            }
            confRead = new Scanner(config);
            new Config();
            /*
            Log.info("Config file:");
            while (confRead.hasNextLine()) {
                Log.info(confRead.nextLine());
            }
             */

        } catch(IOException e) {
            Log.error("Could not create config file, stack trace:");
            e.printStackTrace();
        }
        new Thread(() -> {
            while (running) {
                try {
                    final Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            new UserConnection(socket);
                        } catch (EOFException ex) {
                            Log.warn("Connection terminated");
                        } catch (SocketException ex) {
                            Log.warn("Socket closed");
                        } catch (IOException ex) {
                            Log.error("Caught IOException! Stack trace:");
                            ex.printStackTrace();
                        }
                    }).start();
                } catch (IOException ex) {
                    return;
                }
            }
        }).start();
        Log.info("Listening on "+serverSocket.getInetAddress()+":"+ serverSocket.getLocalPort());
    }
    public static MaintenanceMode getInstance() {
        return instance;
    }
}
