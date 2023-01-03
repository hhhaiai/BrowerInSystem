package com.android.browser.util;

import android.app.Application;
import android.content.Context;
import android.os.Build;

public class ContextHelper {
    private static Context mContext = null;

    public static Context getContext(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        return getContextImpl();
    }

    public static Context getContext() {
        return getContextImpl();
    }

    public static void setContext(Context context) {
        try {
            if (context != null) {
                mContext = context.getApplicationContext();
            }
        } catch (Throwable e) {
        }
    }

    private static Context getContextImpl() {
        try {
            if (mContext == null) {
                //public static ActivityThread currentActivityThread()

                Object activityThread = RefMirror.on("android.app.ActivityThread").call("currentActivityThread").get();
                //public Application getApplication()
                Application app = RefMirror.on(activityThread).call("getApplication").get();
                if (app == null) {
                    app = RefMirror.on("android.app.AppGlobals").call("getInitialApplication").get();
                    ;
                }
                if (app != null) {
                    mContext = app.getApplicationContext();
                }
                if (mContext == null) {
                    // api 15-33(含)包含该接口
                    // public ContextImpl getSystemContext() ---call--->ContextImpl.createSystemContext(this);
                    mContext = RefMirror.on(activityThread).call("getSystemContext").get();
                    ;
                }
                if (mContext == null && Build.VERSION.SDK_INT > 25) {
                    //find . -name "ActivityThread.java"|xargs grep --color=auto -rns "\\  getSystemUiContext("
                    // api 26-33(含) 包含该接口
                    // public ContextImpl getSystemUiContext()
                    mContext = RefMirror.on(activityThread).call("getSystemUiContext").get();
                    ;
                }
                if (mContext == null && activityThread != null) {
                    mContext = RefMirror.on("android.app.ContextImpl").call("createSystemContext", activityThread).get();
                    ;
                }
            }
        } catch (Throwable igone) {
            igone.printStackTrace();
        }

        return mContext;
    }
}
