package com.sampson.android.nodejsmanual.core.persistence;

import android.content.SharedPreferences;

import com.sampson.android.nodejsmanual.utils.ContextUtil;

/**
 * Created by chengyang on 2017/8/4.
 */

public class AppPreference {

    private static SharedPreferences preference = null;

    private final static String XIANDOU_USER = "NODE_JS";

    private static void instance() {
        if (preference == null) {
            preference = ContextUtil.getContext().getSharedPreferences(XIANDOU_USER, 0);
        }
    }

    public static String getLastReadPage() {
        instance();
        String s = null;
        if (preference.contains(LAST_READ_PAGE)) {
            s = preference.getString(LAST_READ_PAGE, null);
        }
        return s;
    }

    public static void setLastReadPage(String page) {
        instance();
        SharedPreferences.Editor ed = preference.edit();
        ed.putString(LAST_READ_PAGE, page);
        ed.commit();
    }

    private final static String LAST_READ_PAGE = "lastReadPage";
}
