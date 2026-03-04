package com.fxffxt.preferen

import android.os.Handler
import kotlin.reflect.KProperty

abstract class ConfigObserver {
    val keys: HashSet<String>
    var handler: Handler? = null
        private set

    // 当传入的key为空时，表示监听该config下所有数据的变化
    // 响应时，全局监听会比指定key监听后收到消息
    // handler为空时，表示不指定线程，哪个线程触发的变化就在哪个线程执行回调
    constructor(handler: Handler, vararg key: String) {
        this.keys = key.toHashSet()
        this.handler = handler
    }
    constructor(vararg key: String) {
        this.keys = key.toHashSet()
        this.handler = null
    }
    constructor() {
        this.keys = HashSet()
        this.handler = null
    }

    constructor(handler: Handler? = null) {
        this.keys = HashSet()
        this.handler = handler
    }

    constructor(handler: Handler, vararg property: KProperty<*>) {
        this.keys = property.map { it.name }.toHashSet()
        this.handler = handler
    }
    constructor(vararg property: KProperty<*>) {
        this.keys = property.map { it.name }.toHashSet()
        this.handler = null
    }

    abstract fun onConfigChanged(key: String, oldValue: Any?, newValue: Any?)
}