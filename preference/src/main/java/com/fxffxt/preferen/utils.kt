package com.fxffxt.preferen

import kotlin.reflect.KProperty

fun getObservableConfigKeyRef(config: ObservableConfig): Map<KProperty<*>, Any?> {
    return config.keyDef
}

fun getObservableKeysMap(config: ObservableConfig): Map<KProperty<*>, String> {
    return config.keysMap
}