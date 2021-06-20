package com.github.joshj1091.mcserver.protocol.packets.incoming;

import com.github.joshj1091.mcserver.protocol.IncomingPacket;
import lombok.Getter;
import me.slimediamond.maintenancemode.util.ByteReader;
import me.slimediamond.maintenancemode.util.Data;

public class LoginStartPacket extends IncomingPacket {

    @Getter
    private final String name;

    public LoginStartPacket(ByteReader reader) {
        super(reader.getBuffer());

        this.name = Data.readString(reader);
    }

    @Override
    public int getId() {
        return 0x00;
    }
}
