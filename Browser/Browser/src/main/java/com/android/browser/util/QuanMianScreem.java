package com.android.browser.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * https://github.com/panliming-tuya/NotchScreenTool
 */
public class QuanMianScreem {

    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT = 0x00010000;

    public static void makesureOK(Activity context) {
        Window window = context.getWindow();
//        setFullScreenWindowLayoutInDisplayCutout(window);
       // fullPage(window);
    }

    /**
     * 设置应用窗口在华为刘海屏手机使用刘海区
     * @param window 应用页面window对象
     */
    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (Throwable e) {
            Log.e("sanbo", Log.getStackTraceString(e));
        }
    }

    /**
     * 设置应用窗口在华为刘海屏手机使用刘海区
     * @param window 应用页面window对象
     */
    public static void setNotFullScreenWindowLayoutInDisplayCutout(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("clearHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (Throwable e) {
            Log.e("sanbo", Log.getStackTraceString(e));
        }
    }

    /**
     * 全面屏幕兼容
     * @param window
     */
    private static void fullPage(Window window) {

        // 延伸显示区域到刘海
        WindowManager.LayoutParams lp = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(lp);
        // 设置页面全屏显示
        final View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

    /**
     * 适配小米Android O设备
     * 判断是否是刘海屏
     * @return
     */
    private static boolean isNotch() {
        try {
            Method getInt = Class.forName("android.os.SystemProperties").getMethod("getInt", String.class, int.class);
            int notch = (int) getInt.invoke(null, "ro.miui.notch", 0);
            return notch == 1;
        } catch (Throwable ignore) {
        }
        return false;
    }

    /**
     * 适配小米Android O设备
     * 设置显示到刘海区域
     * @param activity
     */
    public void setDisplayInNotch(Activity activity) {
        int flag = 0x00000100 | 0x00000200 | 0x00000400;
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(activity.getWindow(), flag);
        } catch (Exception ignore) {
        }
    }

    /**
     * 适配小米Android O设备
     * 获取刘海高
     * @param context
     * @return
     */
    public static int getNotchHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 适配小米Android O设备
     * 获取刘海宽
     * @param context
     * @return
     */
    public static int getNotchWidth(Context context) {
        int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 适配oppoAndroid O设备
     * 判断是否是刘海屏
     * @param activity
     * @return
     */
    public boolean hasNotch(Activity activity) {
        boolean ret = false;
        try {
            ret = activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        } catch (Throwable ignore) {
        }
        return ret;
    }


    /**
     * 适配oppoAndroid O设备------获取刘海的左上角和右下角的坐标
     * 获取刘海的坐标
     * <p>
     * 属性形如：[ro.oppo.screen.heteromorphism]: [378,0:702,80]
     * <p>
     * 获取到的值为378,0:702,80
     * <p>
     * <p>
     * (378,0)是刘海区域左上角的坐标
     * <p>
     * (702,80)是刘海区域右下角的坐标
     */
    private static String getScreenValue() {
        String value = "";
        Class<?> cls;
        try {
            cls = Class.forName("android.os.SystemProperties");
            Method get = cls.getMethod("get", String.class);
            Object object = cls.newInstance();
            value = (String) get.invoke(object, "ro.oppo.screen.heteromorphism");
        } catch (Throwable ignore) {
        }
        return value;
    }
}
