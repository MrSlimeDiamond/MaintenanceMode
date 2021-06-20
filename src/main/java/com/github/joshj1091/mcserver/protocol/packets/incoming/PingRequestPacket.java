package com.github.joshj1091.mcserver.protocol.packets.incoming;

import com.github.joshj1091.mcserver.protocol.IncomingPacket;
import lombok.Getter;
import me.slimediamond.maintenancemode.util.ByteReader;

import java.util.Arrays;

/**
 * This class represents the ping request packet from the client.
 * <p>
 * Standard Packet Format
 * <p>
 * | Field            | Data Type      |
 * -------------------------------------
 * | Packet Size      | VarInt         |
 * | Packet ID        | VarInt         |
 * | Data             | Byte Array     |
 * <p>
 * Ping Request Data Format
 * <p>
 * | Field            | Data Type      |
 * -------------------------------------
 * | Long             | Long           |
 */
public class PingRequestPacket extends IncomingPacket {

    /**
     * The bytes that represent the long sent from the Minecraft client
     * <p>
     * the bytes in an array
     */
    @Getter
    private final byte[] longBytes;

    public PingRequestPacket(ByteReader reader) {
        super(reader.getBuffer());

        this.longBytes = Arrays.copyOfRange(reader.getBuffer(), 1, getBytes().length); // this should always be 8 bytes
    }

    public int getId() {
        return 0x01;
    }
}
