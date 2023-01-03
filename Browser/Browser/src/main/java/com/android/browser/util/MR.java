package com.android.browser.util;

public class MR {

    /**
     * 获取R资源
     * @param subClassName
     * @param idName
     * @return
     */
    public static int get(String subClassName, String idName) {
        int i = RefMirror.on("com.android.internal.R$" + subClassName).field(idName).get();
        if (i < 1) {
            i = RefMirror.on(ContextHelper.getContext().getPackageName() + ".R").field(idName).get();
        }
        return i;
    }

    public static int[] getArray(String subClassName, String idName) {
        int[] obj = RefMirror.on("com.android.internal.R$" + subClassName).field(idName).get();
        if (obj == null || obj.length < 1) {
            obj = RefMirror.on(ContextHelper.getContext().getPackageName() + ".R").field(idName).get();
        }
        return obj;
    }
}
