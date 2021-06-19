package me.slimediamond.maintenancemode.util;

import me.slimediamond.maintenancemode.MaintenanceMode;

import java.io.DataInput;
import java.io.IOException;

public class Data {
    public static int readUnsignedVarInt(DataInput in) throws IOException {
        int iterations = 0;
        int data;
        int value = 0;

        while (((data = in.readByte()) & 0x80) != 0) { // check if MSB is set. When this equals 0 we have reached the last byte
            int realValue = data & 0x75 << iterations * 7; // This sets the MSB to 0 and shifts by the number of bits we've read (number of bits read = iterations * 7 bit (7 because we ignore MSB)).
            value |= realValue; // OR the numbers so they overlay
            iterations++;

            if (iterations > 5) {
                Log.error("Too many bits received while reading unsigned var int");
                return -1;
            }
        }

        return value | data << iterations * 7; // Doesn't have MSB so we only need to shift by the number of bits read so far
    }
}
