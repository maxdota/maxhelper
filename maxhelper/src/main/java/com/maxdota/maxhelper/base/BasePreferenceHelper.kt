package com.maxdota.maxhelper.base

import android.app.Application
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

open class BasePreferenceHelper protected constructor(
    application: Application
) {
    protected val mSharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            application
        )
    }
    protected var mGson: Gson = Gson()
    private val mSampleType = (object : TypeToken<Map<Long, List<String>>>() {}).type

    fun saveString(key: String, value: String?) {
        mSharedPreferences.edit {
            putString(key, value)
        }
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        mSharedPreferences.edit {
            putInt(key, value)
        }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        mSharedPreferences.edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun saveLong(key: String, value: Long) {
        mSharedPreferences.edit {
            putLong(key, value)
        }
    }

    fun getLong(key: String, defaultValue: Long = 0): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    fun getArrayList(key: String, type: Type): ArrayList<Any?> {
        val json = mSharedPreferences.getString(key, null)
        return if (TextUtils.isEmpty(json)) {
            ArrayList()
        } else mGson.fromJson(json, type)
    }

    fun getHashMap(key: String, type: Type?): HashMap<Any, Any?> {
        val json = mSharedPreferences.getString(key, null)
        return if (TextUtils.isEmpty(json)) {
            HashMap()
        } else mGson.fromJson(json, type)
    }

    fun saveObject(key: String, obj: Any?) {
        mSharedPreferences.edit {
            putString(key, mGson.toJson(obj))
        }
    }

    fun getObject(key: String?, objectClass: Class<*>): Any? {
        val json = mSharedPreferences.getString(key, null)
        return if (TextUtils.isEmpty(json)) {
            null
        } else mGson.fromJson(json, objectClass)
    }

    fun clearData(keys: Array<String>) {
        mSharedPreferences.edit {
            for (key in keys) {
                remove(key)
            }
        }
    }

    fun clearAllData() {
        mSharedPreferences.edit {
            clear()
        }
    }
}