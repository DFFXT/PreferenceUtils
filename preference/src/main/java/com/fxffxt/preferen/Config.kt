package com.fxffxt.preferen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken

interface Config {
    val localFileName: String

    fun getMode(): Int {
        return Context.MODE_PRIVATE
    }

    // 可重载该方法，用于加密或者替换MMKV等方式存储
    fun getSharedPreference(): SharedPreferences {
        return ctx.getSharedPreferences(localFileName, getMode())
    }

    fun deleteAll() {
        getSharedPreference().edit().clear().apply()
    }

    /**
     * 如果没有设置key，可以使用 ref::variable.name 作为key
     */
    fun delete(key: String) {
        getSharedPreference().edit().remove(key).apply()
    }

    companion object {
        @JvmStatic
        val ctx: Application by lazy {
            val application =
                Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                    .invoke(null) as Application
            application
        }
    }
}

inline fun <reified T> noneNull(def: T, key: String? = null) =
    ConfigCore.getReadWriteProperty(def, object : TypeToken<T>() {}.type, key)

inline fun <reified T> nullable(def: T? = null, key: String? = null) =
    ConfigCore.getReadWriteProperty(def, object : TypeToken<T>() {}.type, key)
