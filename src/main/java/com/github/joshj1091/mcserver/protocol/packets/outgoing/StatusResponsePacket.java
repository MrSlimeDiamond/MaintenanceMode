package com.github.joshj1091.mcserver.protocol.packets.outgoing;

import com.github.joshj1091.mcserver.protocol.OutgoingPacket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.slimediamond.maintenancemode.util.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * This class represents the clientbound status response packet.
 * <p>
 * More details on this packet can be found here:
 * http://wiki.vg/Server_List_Ping
 */
public class StatusResponsePacket implements OutgoingPacket {

    private final String versionName;
    private final int protocolVersion;
    private final int maxPlayers;
    private final int onlinePlayers;
    private final String descriptionText;

    public StatusResponsePacket(String versionName, int protocolVersion, int maxPlayers, int onlinePlayers, String descriptionText) {
        this.versionName = versionName;
        this.protocolVersion = protocolVersion;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.descriptionText = descriptionText;
    }

    /**
     * This produces a byte array that contains the packet id, the number of bytes in the json string,
     * and the json string.
     *
     * @return byte array
     */
    @Override
    public byte[] encode() {

        JsonObject payload = new JsonObject();

        JsonObject version = new JsonObject();
        version.addProperty("name", versionName);
        version.addProperty("protocol", protocolVersion);

        payload.add("version", version);

        JsonObject players = new JsonObject();
        players.addProperty("max", maxPlayers);
        players.addProperty("online", onlinePlayers);

        payload.add("players", players);

        JsonObject description = new JsonObject();
        description.addProperty("text", descriptionText);

        payload.add("description", description);

        Gson gson = new Gson();
        byte[] jsonBytes = gson.toJson(payload).getBytes();

        byte[] jsonLength = Data.intToUnsignedVarInt(jsonBytes.length);

        byte[] packetId = Data.intToUnsignedVarInt(getId());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(packetId);
            outputStream.write(jsonLength);
            outputStream.write(jsonBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public int getId() {
        return 0x00;
    }
}
