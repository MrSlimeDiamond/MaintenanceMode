package com.github.joshj1091.mcserver.protocol;

import lombok.Getter;

public abstract class IncomingPacket implements Packet {

    @Getter
    private final byte[] bytes;

    protected IncomingPacket(byte[] bytes) {
        this.bytes = bytes;
    }
}
