package com.github.joshj1091.mcserver.protocol;

public interface OutgoingPacket extends Packet {

    byte[] encode();
}
