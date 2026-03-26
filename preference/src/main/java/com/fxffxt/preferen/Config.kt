package com.fxffxt.preferen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class Config {
    abstract val localFileName: String

    // 各个属性的默认值map
    internal val keyDef = HashMap<String, Any?>()

    //各个属性的key map,value是sp的真实key
    internal val keysMap = HashMap<String, String>()
    open fun getMode(): Int {
        return Context.MODE_PRIVATE
    }

    // 可重载该方法，用于加密或者替换MMKV等方式存储
    open fun getSharedPreference(): SharedPreferences {
        return ctx.getSharedPreferences(localFileName, getMode())
    }

    open fun <T> getValue(key: String): T? {
        val value = getSharedPreference().all[key]
        if (value != null) return value as? T
        for ((k, v) in keysMap) {
            if (v == key) {
                return keyDef[k] as? T
            }
        }
        return null
    }

    open fun <T> getValue(property: KProperty<*>): T? {
        return (getSharedPreference().all[keysMap[property.name]] ?: keyDef[property.name]) as? T
    }

    open fun deleteAll() {
        getSharedPreference().edit().clear().apply()
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
): ReadWritePropertyDelegateProvider<T> =
    ReadWritePropertyDelegateProvider(def, object : TypeToken<T>() {}.type, key, apply)

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
        return ConfigCore.getReadWriteProperty(
            def,
            type,
            property,
            key ?: property.name,
            thisRef,
            apply
        )
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
        return ConfigCore.getReadWriteProperty(
            def,
            type,
            property,
            key ?: property.name,
            thisRef,
            apply
        )
    }
}

inline fun <reified T> Config.noneNull(
    def: T,
    key: String? = null,
    apply: Boolean = true
): ReadWritePropertyDelegateProvider2<T> =
    ReadWritePropertyDelegateProvider2(def, object : TypeToken<T>() {}.type, key)

//inline fun <reified T> Config.nullable(def: T? = null, key: String? = null):ReadWriteProperty<Config, T?> =
//    ConfigCore.getReadWriteProperty(def, object : TypeToken<T>() {}.type, key, this)
