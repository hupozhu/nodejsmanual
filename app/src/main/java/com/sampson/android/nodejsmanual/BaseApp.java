package com.sampson.android.nodejsmanual;

import android.app.Application;

import com.sampson.android.nodejsmanual.core.AppCache;
import com.sampson.android.nodejsmanual.utils.ScreenUtils;
import com.sampson.android.nodejsmanual.utils.ToastUtils;

/**
 * Created by chengyang on 2017/1/3.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppCache.init(this);
        ScreenUtils.init(this);
        ToastUtils.init(this);
    }
}
