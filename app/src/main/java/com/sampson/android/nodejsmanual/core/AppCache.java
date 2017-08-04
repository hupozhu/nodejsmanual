package com.sampson.android.nodejsmanual.core;

import android.app.Application;
import android.content.Context;

/**
 * Created by chengyang on 2017/6/13.
 */

public class AppCache {

    private Context mContext;

    public static int cropNum = 0;

    //============== instance ================
    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache sAppCache = new AppCache();
    }

    private static AppCache getInstance() {
        return SingletonHolder.sAppCache;
    }

    public static void init(Application application) {
        getInstance().onInit(application);
    }

    private void onInit(Application application) {
        mContext = application.getApplicationContext();
    }

    // ================= data ===================
    public static Context getContext() {
        return getInstance().mContext;
    }


}
