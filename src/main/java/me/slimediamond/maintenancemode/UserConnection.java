package me.slimediamond.maintenancemode;

import com.github.joshj1091.mcserver.protocol.Direction;
import com.github.joshj1091.mcserver.protocol.Packet;
import com.github.joshj1091.mcserver.protocol.Protocol;
import com.github.joshj1091.mcserver.protocol.packets.incoming.HandshakePacket;
import com.github.joshj1091.mcserver.protocol.packets.incoming.LoginStartPacket;
import com.github.joshj1091.mcserver.protocol.packets.incoming.PingRequestPacket;
import com.github.joshj1091.mcserver.protocol.packets.outgoing.LoginDisconnectPacket;
import com.github.joshj1091.mcserver.protocol.packets.outgoing.PongResponsePacket;
import com.github.joshj1091.mcserver.protocol.packets.outgoing.StatusResponsePacket;
import me.slimediamond.maintenancemode.util.ByteReader;
import me.slimediamond.maintenancemode.util.Data;
import me.slimediamond.maintenancemode.util.Log;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static me.slimediamond.maintenancemode.MaintenanceMode.prop;

public class UserConnection {
    private final Socket socket;
    int state = 0;
    boolean accept = true;
    public UserConnection(final Socket socket) throws IOException {
        this.socket = socket;
        Log.debug("Recieved ping from " + socket.getInetAddress().toString());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        while (accept) {
            int size = Data.readUnsignedVarInt(inputStream);
            byte[] buffer = new byte[size];
            inputStream.readFully(buffer);
            ByteReader reader = new ByteReader(buffer);
            int id = Data.readUnsignedVarInt(reader);
            Packet packet = Protocol.getPacket(state, Direction.SERVERBOUND, reader, id);
            handle(packet);
        }
    }
    private void handle(Packet packet) {
        if (state == 0) {
            if (packet.getId() == 0x00) { // handshake packet
                HandshakePacket handshakePacket = (HandshakePacket) packet;
                this.state = handshakePacket.getNextState();
            }
        } else if (state == 1) {
            if (packet.getId() == 0x00) { // status request
                Log.debug("Got status request");

                Log.debug("Version: "+prop.getProperty("version"));
                Log.debug("MOTD: "+prop.getProperty("motd"));
                StatusResponsePacket response = new StatusResponsePacket(prop.getProperty("version"), 0, 0, 0, prop.getProperty("motd"));
                sendData(response.encode());
            } else if (packet.getId() == 0x01) {
                Log.debug("Got ping request");

                PingRequestPacket pingRequestPacket = (PingRequestPacket) packet;
                PongResponsePacket response = new PongResponsePacket(pingRequestPacket.getLongBytes());
                sendData(response.encode());
            }
        } else if (state == 2) {
            if (packet.getId() == 0x00) {
                Log.debug("Login start packet");
                LoginStartPacket loginStartPacket = (LoginStartPacket) packet;
                Log.debug("Found name: " + loginStartPacket.getName());

                LoginDisconnectPacket loginDisconnectPacket = new LoginDisconnectPacket(prop.getProperty("kickmsg"));
                sendData(loginDisconnectPacket.encode());
            }
        }
    }

    private void sendData(byte[] data) {
        byte[] dataLength = Data.intToUnsignedVarInt(data.length);

        write(dataLength);
        write(data);
    }

    private void write(byte[] data) {
        try {
            socket.getOutputStream().write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() throws IOException {
        accept = false;
        socket.close();
    }
}
