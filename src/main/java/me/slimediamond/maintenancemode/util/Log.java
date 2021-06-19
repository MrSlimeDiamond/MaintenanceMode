package me.slimediamond.maintenancemode.util;

public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static StackTraceElement getCallerClassName1() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(KDebug.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                if (callerClassName==null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste;
                }
            }
        }
        return null;
    }
    public static String getCallerClassName() {
        String classNameFull = getCallerClassName1().getFileName();
        int lineNum = getCallerClassName1().getLineNumber();
        String className[] = classNameFull.split(".java");
        return className[0]+":"+lineNum;
    }

    public static void info(String in) {
        System.out.println("["+getCallerClassName()+"/"+ANSI_CYAN+"INFO"+ANSI_RESET+"] "+in);
    }
    public static void error(String in) {
        System.out.println(ANSI_RED+"["+getCallerClassName()+"/ERROR] "+in+ANSI_RESET);
    }
    public static void warn(String in) {
        System.out.println(ANSI_YELLOW+"["+getCallerClassName()+"/WARN] "+in+ANSI_RESET);
    }
    public static void test(String in) {
        System.out.println("["+getCallerClassName()+"/"+ANSI_GREEN+"TEST"+ANSI_RESET+"] "+in);
    }
    public static void debug(String in) {
        System.out.println("["+getCallerClassName()+"/"+ANSI_PURPLE+"DEBUG"+ANSI_RESET+"] "+in);
    }
}
