package com.fxffxt.preferen

import kotlin.reflect.KProperty

fun getObservableConfigKeyRef(config: Config): Map<String, Any?> {
    return config.keyDef
}

fun getObservableKeysMap(config: Config): Map<String, String> {
    return config.keysMap
}

fun getProviderMap(config: Config): Map<String, CustomReadWriteProperty<Config, *>> {
    return config.provider
}
