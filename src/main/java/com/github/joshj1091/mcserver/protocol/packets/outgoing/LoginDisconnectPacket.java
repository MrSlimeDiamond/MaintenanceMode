package com.github.joshj1091.mcserver.protocol.packets.outgoing;

import com.github.joshj1091.mcserver.protocol.OutgoingPacket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.slimediamond.maintenancemode.util.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LoginDisconnectPacket implements OutgoingPacket {

    private final String disconnectMessage;

    public LoginDisconnectPacket(String disconnectMessage) {
        this.disconnectMessage = disconnectMessage;
    }

    @Override
    public byte[] encode() {
        JsonObject payload = new JsonObject();

        payload.addProperty("text", disconnectMessage);

        byte[] packetId = Data.intToUnsignedVarInt(getId());
        byte[] messageBytes = new Gson().toJson(payload).getBytes();
        byte[] messageLength = Data.intToUnsignedVarInt(messageBytes.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            outputStream.write(packetId);
            outputStream.write(messageLength);
            outputStream.write(messageBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public int getId() {
        return 0x00;
    }
}
