package com.maxdota.maxhelper.base;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Abc on 28/06/2017.
 */

public class BasePreferenceHelper {
    protected SharedPreferences mSharedPreferences;
    protected Gson mGson;
    private Type mSampleType;

    protected BasePreferenceHelper() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.sApplication);
        mGson = new Gson();
        mSampleType = new TypeToken<ArrayList<String>>() {
        }.getType();
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void saveLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public ArrayList getArrayList(String key, Type type) {
        String json = mSharedPreferences.getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return mGson.fromJson(json, type);
    }

    public HashMap getHashMap(String key, Type type) {
        String json = mSharedPreferences.getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return new HashMap<>();
        }
        return mGson.fromJson(json, type);
    }

    public void saveObject(String key, Object object) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, mGson.toJson(object));
        editor.apply();
    }

    public Object getObject(String key, Class<?> objectClass) {
        String json = mSharedPreferences.getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return mGson.fromJson(json, objectClass);
    }

    public void clearData(String[] keys) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }
}
