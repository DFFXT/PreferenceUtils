package com.fxffxt.preferen;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/**
 * 通过 java 的可空特性少写一半代码
 */
public class ConfigCore {
    public static <T> ReadWriteProperty<Config, T> getReadWriteProperty(@Nullable T def, Class<T> type, String key) {
        return new ReadWriteProperty<Config, T>() {
            @Override
            public T getValue(Config thisRef, @NotNull KProperty<?> property) {
                SharedPreferences sp = thisRef.getSharedPreference();
                String mKey = CoreKt.isNullOrEmptyByCandidate(key, property.getName());
                if (sp.contains(mKey)) {
                    Object v = null;
                    if (String.class.equals(type)) {
                        v = sp.getString(mKey, (String) def);
                    }else if (Integer.class.equals(type)) {
                        v = sp.getInt(mKey, def == null ? 0 : (Integer) def);
                    } else if (Long.class.equals(type)) {
                        v = sp.getLong(mKey, def == null ? 0L : (Long) def);
                    } else if (Boolean.class.equals(type)) {
                        v = sp.getBoolean(mKey, def != null && (Boolean) def);
                    } else if (Float.class.equals(type)) {
                        v = sp.getFloat(mKey, def == null ? 0f : (Float) def);
                    }else {
                        v = new Gson().fromJson(sp.getString(mKey, null), type);
                    }
                    if (v == null) return def;
                    try {
                        return (T) v;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                } else {
                    return def;
                }

            }

            @Override
            public void setValue(Config thisRef, @NotNull KProperty<?> property, T value) {
                SharedPreferences sp = thisRef.getSharedPreference();
                String mKey = CoreKt.isNullOrEmptyByCandidate(key, property.getName());
                if (value != null) {
                    SharedPreferences.Editor editor = sp.edit();
                    if (String.class.equals(type)) {
                        editor.putString(mKey, (String) value);
                    } else if (Integer.class.equals(type)) {
                        editor.putInt(mKey, (Integer) value);
                    } else if (Long.class.equals(type)) {
                        editor.putLong(mKey, (Long) value);
                    } else if (Boolean.class.equals(type)) {
                        editor.putBoolean(mKey, (Boolean) value);
                    } else if (Float.class.equals(type)) {
                        editor.putFloat(mKey, (Float) value);
                    }else {
                        editor.putString(mKey, new Gson().toJson(value));
                    }
                    editor.apply();

                } else if (sp.contains(mKey)) {
                    thisRef.getSharedPreference()
                            .edit()
                            .remove(mKey)
                            .apply();
                }
            }
        };
    }
}
