package com.github.joshj1091.mcserver.protocol.packets.incoming;

import com.github.joshj1091.mcserver.protocol.IncomingPacket;
import me.slimediamond.maintenancemode.util.ByteReader;

/**
 * This class represents the status request packet. This packet doesn't have a payload.
 */
public class StatusRequestPacket extends IncomingPacket {

    public StatusRequestPacket(ByteReader reader) {
        super(reader.getBuffer());
    }

    public int getId() {
        return 0x00;
    }
}
