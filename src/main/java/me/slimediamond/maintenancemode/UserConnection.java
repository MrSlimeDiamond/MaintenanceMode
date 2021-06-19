package me.slimediamond.maintenancemode;

import me.slimediamond.maintenancemode.util.ByteReader;
import me.slimediamond.maintenancemode.util.Data;
import me.slimediamond.maintenancemode.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

import static me.slimediamond.maintenancemode.MaintenanceMode.confRead;
import static me.slimediamond.maintenancemode.MaintenanceMode.serverSocket;

public class UserConnection {
    enum Direction {
        SERVERBOUND,
        CLIENTBOUND;
    }
    interface Packet {
        int getId();
    }
    public UserConnection(final Socket socket) throws IOException {
        int state = 0;
        Log.debug("Recieved ping from " + socket.getInetAddress().toString());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        while (true) {
            int size = Data.readUnsignedVarInt(inputStream);
            byte[] buffer = new byte[size];
            inputStream.readFully(buffer);
            ByteReader reader = new ByteReader(buffer);
        }
    }
}
