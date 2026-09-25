package com.maxdota.core.base

import android.app.Application
import android.text.TextUtils
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

open class BasePreferenceHelper(val application: Application) {
  protected val mSharedPreferences by lazy {
    PreferenceManager.getDefaultSharedPreferences(application)
  }
  protected var mGson: Gson = Gson()
  private val mSampleType =
    (object : TypeToken<Map<Long, List<String>>>() {}).type

  fun saveString(key: String, value: String?) {
    val editor = mSharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
  }

  fun getString(key: String, defaultValue: String? = null): String? {
    return mSharedPreferences.getString(key, defaultValue)
  }

  fun saveInt(key: String, value: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt(key, value)
    editor.apply()
  }

  fun getInt(key: String, defaultValue: Int = 0): Int {
    return mSharedPreferences.getInt(key, defaultValue)
  }

  fun saveBoolean(key: String, value: Boolean) {
    val editor = mSharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
  }

  fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
    return mSharedPreferences.getBoolean(key, defaultValue)
  }

  fun saveLong(key: String, value: Long) {
    val editor = mSharedPreferences.edit()
    editor.putLong(key, value)
    editor.apply()
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
    val editor = mSharedPreferences.edit()
    editor.putString(key, mGson.toJson(obj))
    editor.apply()
  }

  fun getObject(key: String?, objectClass: Class<*>): Any? {
    val json = mSharedPreferences.getString(key, null)
    return if (TextUtils.isEmpty(json)) {
      null
    } else mGson.fromJson(json, objectClass)
  }

  fun clearData(keys: Array<String>) {
    val editor = mSharedPreferences.edit()
    for (key in keys) {
      editor.remove(key)
    }
    editor.apply()
  }

  fun clearAllData() {
    val editor = mSharedPreferences.edit()
    editor.clear()
    editor.apply()
  }
}