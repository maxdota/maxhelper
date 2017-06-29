package com.maxdota.maxhelper.base;

import android.app.Application;

import com.maxdota.maxhelper.R;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

    public void setUpPicassoOfflineSupport() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                // to support Facebook image caching
                Response response = originalResponse.newBuilder()
                        .removeHeader("pragma")
                        .removeHeader("expires")
                        .header("cache-control", "max-age=" + (60 * 60 * 24 * 365)).build();
                return response;
            }
        });
        okHttpClient.setCache(new Cache(getCacheDir(), Integer.MAX_VALUE));
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(okHttpClient));
        Picasso built = builder.build();
        Picasso.setSingletonInstance(built);
    }
}
