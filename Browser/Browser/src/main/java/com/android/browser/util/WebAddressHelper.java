package com.android.browser.util;

import java.lang.reflect.Constructor;

public class WebAddressHelper {

    public static Object getWebAddress(String url) {
        Object address = null;
        try {
            Class c = Class.forName("android.net.WebAddress");
            Constructor cc = c.getDeclaredConstructor(String.class);
            address = cc.newInstance(url);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return address;
    }
}
