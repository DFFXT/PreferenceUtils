package com.fxffxt.preferen

import kotlin.reflect.KProperty

abstract class ConfigObserver {
    val key: String
    constructor(key: String) {
        this.key = key
    }
    constructor(property: KProperty<*>) {
        this.key = property.name
    }
    abstract fun onConfigChanged(key: String, oldValue: Any?, newValue: Any?)
}