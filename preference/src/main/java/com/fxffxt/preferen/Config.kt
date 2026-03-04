package com.fxffxt.preferen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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


/**
 * 使用provideDelegate来代理属性访问，这样可以提前拿到KProperty
 */
inline fun <reified T> Config.nullable(
    def: T? = null,
    key: String? = null,
    apply: Boolean = true
): ReadWritePropertyDelegateProvider<T> = ReadWritePropertyDelegateProvider(def, object : TypeToken<T>() {}.type, key, apply)
class ReadWritePropertyDelegateProvider<T>(
    private val def: T?,
    private val type: java.lang.reflect.Type,
    private val key: String?,
    private val apply: Boolean
) {
    operator fun provideDelegate(
        thisRef: Config,
        property: KProperty<*>
    ): ReadWriteProperty<Config, T?> {
        return ConfigCore.getReadWriteProperty(def, type, key ?: property.name, thisRef, apply)
    }
}

class ReadWritePropertyDelegateProvider2<T>(
    private val def: T?,
    private val type: java.lang.reflect.Type,
    private val key: String?,
    private val apply: Boolean = true
) {
    operator fun provideDelegate(
        thisRef: Config,
        property: KProperty<*>
    ): ReadWriteProperty<Config, T> {
        return ConfigCore.getReadWriteProperty(def, type, key ?: property.name, thisRef, apply)
    }
}

inline fun <reified T> Config.noneNull(def: T, key: String? = null, apply: Boolean = true): ReadWritePropertyDelegateProvider2<T> = ReadWritePropertyDelegateProvider2(def, object : TypeToken<T>() {}.type, key)

//inline fun <reified T> Config.nullable(def: T? = null, key: String? = null):ReadWriteProperty<Config, T?> =
//    ConfigCore.getReadWriteProperty(def, object : TypeToken<T>() {}.type, key, this)
