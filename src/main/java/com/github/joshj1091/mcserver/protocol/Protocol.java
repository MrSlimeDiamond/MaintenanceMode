package com.github.joshj1091.mcserver.protocol;

import com.github.joshj1091.mcserver.protocol.packets.incoming.HandshakePacket;
import com.github.joshj1091.mcserver.protocol.packets.incoming.LoginStartPacket;
import com.github.joshj1091.mcserver.protocol.packets.incoming.PingRequestPacket;
import com.github.joshj1091.mcserver.protocol.packets.incoming.StatusRequestPacket;
//import com.github.joshj1091.mcserver.util.ByteReader;
import me.slimediamond.maintenancemode.util.ByteReader;

public class Protocol {

    private Protocol() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the packet type based on the state, direction and id
     *
     * @param state     the state
     * @param direction the direction of the packet
     * @param id        the id of the packet
     * @param reader    byteReader
     * @return the type of packet
     */
    public static Packet getPacket(int state, Direction direction, ByteReader reader, int id) {
        if (state == 0) {
            if (direction == Direction.SERVERBOUND) {
                if (id == 0x00) {
                    return new HandshakePacket(reader);
                }
            }
        } else if (state == 1) {
            if (direction == Direction.SERVERBOUND) {
                if (id == 0x00) {
                    return new StatusRequestPacket(reader);
                } else if (id == 0x01) {
                    return new PingRequestPacket(reader);
                }
            }
        } else if (state == 2) {
            if (direction == Direction.SERVERBOUND) {
                if (id == 0x00) {
                    return new LoginStartPacket(reader);
                }
            }
        }

        return null;
    }
}
