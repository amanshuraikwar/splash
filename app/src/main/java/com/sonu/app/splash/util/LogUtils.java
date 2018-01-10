package com.sonu.app.splash.util;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class LogUtils {

    public static String getLogTag(Class cls) {
        return cls.getSimpleName();
    }
}
