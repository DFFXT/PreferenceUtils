package com.fxffxt.preferen

import java.util.LinkedList

interface ConfigObserverDispatcher {
    fun addObserver(observer: ConfigObserver)

    fun removeObserver(observer: ConfigObserver)

    /**
     * 监听配置变化
     * 无法监到delete和deleteAll操作，因为某些存在默认值
     */
    fun dispatch(key: String, oldValue: Any?, newValue: Any?)
}