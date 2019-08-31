package com.pofb.library.model;

import cz.msebera.android.httpclient.protocol.HttpContext;

public class HttpContextBundle {
    private static HttpContext context;

    public static HttpContext getContext() {
        if (context == null) {
            throw new NoClassDefFoundError();
        } else {
            return context;
        }
    }

    public static void setContext(HttpContext context) {
        HttpContextBundle.context = context;
    }
}
