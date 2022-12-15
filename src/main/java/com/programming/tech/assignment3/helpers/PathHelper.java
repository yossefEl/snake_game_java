package com.programming.tech.assignment3.helpers;

/*
*because UNIX based operating systems and Windows have different syntax
* so to make that all the assets and access files requests works properly
* This why this class helper created ..it's for handling paths
*/

import java.util.regex.Matcher;

public class PathHelper {
    /*
     * In case the program is running under a windows OS this will convert the path
     * to Windows path syntax
     * else it return the same format ... because MacOS and Linux distros are both
     * based on UNIX
     * and their syntax are same
     */

    public static String toCurrentOSPathSyntax(String unixPathSyntax) {
        if (isWindows()) {
            if (unixPathSyntax == null)
                return "";
            return unixPathSyntax.replaceAll("/", Matcher.quoteReplacement("\\"));
        }
        return unixPathSyntax;
    }

    /*
     * get the current os name
     * if windows this function will return true
     * else it will return false
     */
    public static boolean isWindows() {
        String OSName = System.getProperty("os.name");
        return OSName.startsWith("Windows");
    }

    /*
     * returns the current project directory [the location of Main.java file]
     */
    public static String getProjectLocation() {
        // System.out.println(System.getProperty("user.dir"));
        return System.getProperty("user.dir");
    }

    public final static String PROJECT_ASSETS_PATH = getProjectLocation()
            + toCurrentOSPathSyntax("/src/main" + toCurrentOSPathSyntax("/assets/"));
}
