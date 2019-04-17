package com.maxdota.maxhelper.base;

import android.app.Application;

import com.maxdota.maxhelper.R;

/**
 * Created by Nguyen Hong Ngoc on 2/13/2017.
 */

public class BaseApplication extends Application {
    public static BaseApplication sApplication;
    public static boolean sIsDebugging = true;
    public static String sAppName;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sAppName = getString(R.string.app_name);
    }
}
