package com.example.osama.dashbike.Utility;

import android.app.Application;
import android.content.Context;

public class ApplicationContext extends Application {
    private static ApplicationContext mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());

    }

    public static ApplicationContext getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}
