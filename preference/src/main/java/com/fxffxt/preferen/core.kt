package com.fxffxt.preferen

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun String?.isNullOrEmptyByCandidate(candidate: String): String {
    return if (this.isNullOrBlank()) candidate else this
}

fun <T> getReadWriteProperty(
    def: T,
    type: Class<T>,
    key: String? = null
): ReadWriteProperty<Config, T> {
    return object : ReadWriteProperty<Config, T> {
        override fun setValue(thisRef: Config, property: KProperty<*>, value: T) {
            val sp = thisRef.getSharedPreference()
            val mKey = key.isNullOrEmptyByCandidate(property.name)
            if (value != null) {
                val editor = sp.edit()
                when (type) {
                    String::class.java -> {
                        editor.putString(mKey, value as String)
                    }
                    Int::class.java -> {
                        editor.putInt(mKey, value as Int)
                    }
                    Long::class.java -> {
                        editor.putLong(mKey, value as Long)
                    }
                    Boolean::class.java -> {
                        editor.putBoolean(mKey, value as Boolean)
                    }
                    Float::class.java -> {
                        editor.putFloat(mKey, value as Float)
                    }
                    else -> {
                        throw Exception("错误的类型${type}")
                    }
                }
                editor.apply()

            } else if (sp.contains(mKey)) {
                thisRef.getSharedPreference()
                    .edit()
                    .remove(mKey)
                    .apply()
            }

        }

        override fun getValue(thisRef: Config, property: KProperty<*>): T {
            val sp = thisRef.getSharedPreference()
            val mKey = key.isNullOrEmptyByCandidate(property.name)
            return if (sp.contains(mKey)) {

                when (type) {
                    String::class.java -> {
                        sp.getString(mKey, def as String) as T
                    }
                    Int::class.java -> {
                        sp.getInt(mKey, def as Int) as T
                    }
                    Long::class.java -> {
                        sp.getLong(mKey, def as Long) as T
                    }
                    Boolean::class.java -> {
                        sp.getBoolean(mKey, def as Boolean) as T
                    }
                    Float::class.java -> {
                        sp.getFloat(mKey, def as Float) as T
                    }
                    else -> {
                        throw Exception("错误的类型${type}")
                    }
                }
            } else def

        }
    }
}

fun <T> getReadWritePropertyNullable(
    type: Class<T>,
    key: String? = null
): ReadWriteProperty<Config, T?> {
    return object : ReadWriteProperty<Config, T?> {
        override fun setValue(thisRef: Config, property: KProperty<*>, value: T?) {
            val sp = thisRef.getSharedPreference()
            val mKey = key.isNullOrEmptyByCandidate(property.name)
            if (value != null) {
                val editor = sp.edit()
                when (type) {
                    String::class.java -> {
                        editor.putString(mKey, value as String)
                    }
                    Int::class.java -> {
                        editor.putInt(mKey, value as Int)
                    }
                    Long::class.java -> {
                        editor.putLong(mKey, value as Long)
                    }
                    Boolean::class.java -> {
                        editor.putBoolean(mKey, value as Boolean)
                    }
                    Float::class.java -> {
                        editor.putFloat(mKey, value as Float)
                    }
                    else -> {
                        throw Exception("错误的类型$type")
                    }
                }
                editor.apply()

            } else if (sp.contains(mKey)) {
                thisRef.getSharedPreference()
                    .edit()
                    .remove(mKey)
                    .apply()
            }

        }

        override fun getValue(thisRef: Config, property: KProperty<*>): T? {
            val sp = thisRef.getSharedPreference()
            val mKey = key.isNullOrEmptyByCandidate(property.name)
            return if (sp.contains(mKey)) {
                when (type) {
                    String::class.java -> {
                        sp.getString(mKey, null) as? T
                    }
                    Int::class.java -> {
                        sp.getInt(mKey, 0) as? T
                    }
                    Long::class.java -> {
                        sp.getLong(mKey, 0L) as? T
                    }
                    Boolean::class.java -> {
                        sp.getBoolean(mKey, false) as? T
                    }
                    Float::class.java -> {
                        sp.getFloat(mKey, 0f) as? T
                    }
                    else -> {
                        throw Exception("错误的类型$type")
                    }
                }
            } else null

        }
    }
}