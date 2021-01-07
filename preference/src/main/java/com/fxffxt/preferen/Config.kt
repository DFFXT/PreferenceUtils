package com.fxffxt.preferen

import android.content.Context
import android.content.SharedPreferences

interface Config {
    val localFileName: String

    open fun getMode(): Int {
        return Context.MODE_PRIVATE
    }
    open fun getSharedPreference():SharedPreferences{
        return ctx.getSharedPreferences(localFileName, getMode())
    }

    open fun deleteAll() {
        getSharedPreference().edit().clear().apply()
    }

    /**
     * 如果没有设置key，可以使用 ref::variable.name 作为key
     */
    open fun delete(key: String){
        getSharedPreference().edit().remove(key).apply()
    }

    companion object {
        @JvmStatic
        lateinit var ctx: Context
    }
}



fun string(def: String, key: String? = null) = getReadWriteProperty(def,String::class.java, key)
fun stringNullable(key: String? = null) = getReadWritePropertyNullable(String::class.java,key)

fun int(def: Int, key: String? = null) = getReadWriteProperty(def,Int::class.java, key)
fun intNullable(key: String? = null) = getReadWritePropertyNullable(Int::class.java,key)

fun long(def: Long, key: String? = null) = getReadWriteProperty(def,Long::class.java, key)
fun longNullable(key: String? = null) = getReadWritePropertyNullable(Long::class.java,key)

fun float(def: Float, key: String? = null) = getReadWriteProperty(def,Float::class.java, key)
fun floatNullable(key: String?) = getReadWritePropertyNullable(Float::class.java, key)

fun boolean(def: Boolean, key: String? = null) = getReadWriteProperty(def,Boolean::class.java, key)
fun booleanNullable(key: String? = null) = getReadWritePropertyNullable(Boolean::class.java,key)


