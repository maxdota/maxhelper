package com.maxdota.maxhelper.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maxdota.maxhelper.MaxHelper;

/**
 * Created by Nguyen Hong Ngoc on 2/13/2017.
 */

public class BaseActivity extends AppCompatActivity {
    // have to find_view in child activity in order to use
    protected View mCircleLoader;
    protected Handler mHandler;
    protected MaxHelper mMaxHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mMaxHelper = new MaxHelper(this);
    }

    public Handler getHandler() {
        return mHandler;
    }

    public MaxHelper getMaxHelper() {
        return mMaxHelper;
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int messageRes) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
    }

    public static void log(String message) {
        if (BaseApplication.IS_DEBUGGING && message != null) {
            Log.e(BaseApplication.sAppName, message);
        }
    }

    public void showCircleLoader() {
        if (mCircleLoader != null) {
            mCircleLoader.setVisibility(View.VISIBLE);
        }
    }

    public void hideCircleLoader() {
        if (mCircleLoader != null) {
            mCircleLoader.setVisibility(View.GONE);
        }
    }
}
