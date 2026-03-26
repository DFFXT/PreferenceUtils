package com.fxffxt.preferen

import kotlin.reflect.KProperty

interface ConfigObserverDispatcher {
    fun addObserver(observer: ConfigObserver)

    fun removeObserver(observer: ConfigObserver)

    /**
     * 监听配置变化
     * 无法监到delete和deleteAll操作，因为某些存在默认值
     */
    fun dispatch(propertyName: String, oldValue: Any?, newValue: Any?)
}