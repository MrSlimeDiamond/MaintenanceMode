package com.github.joshj1091.mcserver.protocol.packets.incoming;

import com.github.joshj1091.mcserver.protocol.IncomingPacket;
import lombok.Getter;
import me.slimediamond.maintenancemode.util.ByteReader;
import me.slimediamond.maintenancemode.util.Data;

/**
 * This class represents the handshake packet
 * <p>
 * Standard Packet Format
 * <p>
 * | Field            | Data Type      |
 * -------------------------------------
 * | Packet Size      | VarInt         |
 * | Packet ID        | VarInt         |
 * | Data             | Byte Array     |
 * <p>
 * Handshake Data Format
 * <p>
 * | Field            | Data Type      |
 * -------------------------------------
 * | Protocol Version | VarInt         |
 * | Server Address   | String         |
 * | Server Port      | Unsigned Short |
 * | Next State       | VarInt         |
 */
public class HandshakePacket extends IncomingPacket {

    @Getter
    private final int protocolVersion;
    @Getter
    private final String serverAddress;
    @Getter
    private final int serverPort;
    @Getter
    private final int nextState;

    public HandshakePacket(ByteReader reader) {
        super(reader.getBuffer());

        protocolVersion = Data.readUnsignedVarInt(reader);
        serverAddress = Data.readString(reader);
        serverPort = Data.readUnsignedShort(reader);
        nextState = Data.readUnsignedVarInt(reader);
    }

    @Override
    public int getId() {
        return 0x00;
    }
}
