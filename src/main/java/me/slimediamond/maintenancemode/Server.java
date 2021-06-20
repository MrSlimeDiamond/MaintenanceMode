package me.slimediamond.maintenancemode;

import me.slimediamond.maintenancemode.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;

import static java.lang.Integer.parseInt;
import static me.slimediamond.maintenancemode.MaintenanceMode.prop;

public class Server {
    public InetAddress addr;
    public int port;
    private final boolean running = true;
    public Server(ServerSocket serverSocket) throws IOException {
        Log.info("Initializing shutdown hook");
        ServerSocket finalServerSocket1 = serverSocket;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Log.info("Shutting down...");
            try {
                finalServerSocket1.close();
            } catch (IOException e) {
                Log.error(e.getStackTrace());
            }
            Log.info("Thank you and goodbye.");
        }));
        try {
            //Log.debug(Config.getOption("port"));
            addr = InetAddress.getByName(prop.getProperty("address"));
            //Log.debug(addr.toString());
        } catch (UnknownHostException e) {
            Log.error("Could not bind to "+addr.toString()+" stack trace:");
            Log.error(e.getStackTrace());
            System.exit(1);
        }
        port = parseInt(prop.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port, 1, addr);
        } catch(BindException e) {
            Log.error("Could not bind to port "+ port);
            Log.error("Maybe there is already a server running on that port?");
            System.exit(1);
        }
        ServerSocket finalServerSocket = serverSocket;
        new Thread(() -> {
            while (running) {
                try {
                    final Socket socket = finalServerSocket.accept();
                    new Thread(() -> {
                        try {
                            new UserConnection(socket);
                        } catch (EOFException ex) {
                            Log.warn("Connection terminated");
                        } catch (SocketException ex) {
                            Log.warn("Socket closed");
                        } catch (IOException ex) {
                            Log.error(ex.getStackTrace());
                        }
                    }).start();
                } catch (IOException ex) {
                    return;
                }
            }
        }).start();
        Log.info("Listening on "+serverSocket.getInetAddress()+":"+ serverSocket.getLocalPort());
    } // Server end
}
