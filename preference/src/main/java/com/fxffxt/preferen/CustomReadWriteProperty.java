package com.fxffxt.preferen;

import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

public interface CustomReadWriteProperty<T, R> extends ReadWriteProperty<T, R> {
    default R getValue(T t) {
        return getValue(t, null);
    }
}
