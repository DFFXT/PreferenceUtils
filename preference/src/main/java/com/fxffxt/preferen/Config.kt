package com.fxffxt.preferen

import android.content.Context
import android.content.SharedPreferences


interface Config {
    val localFileName: String

    fun getMode(): Int {
        return Context.MODE_PRIVATE
    }

    //可重载该方法，用于加密或者替换MMKV等方式存储
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
        lateinit var ctx: Context
    }
}

inline fun <reified T> noneNull(def: T,key: String? = null) = ConfigCore.getReadWriteProperty(def, T::class.java, key)
inline fun <reified T> nullable(def: T? = null, key: String? = null) = ConfigCore.getReadWriteProperty(def, T::class.java, key)

