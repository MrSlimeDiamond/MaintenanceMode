package me.slimediamond.maintenancemode;

import me.slimediamond.maintenancemode.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

import static me.slimediamond.maintenancemode.MaintenanceMode.confRead;

public class Config {
    String motd;
    String kickmsg;
    /* Read config */
    public Config() throws IOException {
        while (confRead.hasNextLine()) {
            String line = confRead.nextLine();
            if (line.startsWith("motd:")) {
                String[] l1 = line.split("motd: ", 0); /* oh, right.. */
                motd = l1[1];
                Log.debug("MOTD is: " + motd);

            } else {
                if (line.startsWith("kickmsg:")) {
                    String[] l1 = line.split("kickmsg: ");
                    kickmsg = l1[1];
                    Log.debug("Kick message is: "+kickmsg);
                }
            }
        }
    }
    public static String[] l1;
    public static String getOption(String opt) throws IOException {
        try {
        Log.debug(opt);
            while (confRead.hasNextLine()) {
            Log.debug("Has next line");
            String line = confRead.nextLine();
            Log.debug("Line: "+line);
            if (line.startsWith(opt+":")) {
                l1 = line.split(opt+": ", 0);
            }
        }
            return l1[1];
        } catch(NullPointerException e) {
            /* Just in case... */
            Log.error("Caught NPE while trying to get config option. Stack trace:");
            e.printStackTrace();
            return "An error occurred, check console!";
        }
    }
}
