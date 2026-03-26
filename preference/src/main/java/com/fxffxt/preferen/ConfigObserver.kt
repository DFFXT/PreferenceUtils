package com.fxffxt.preferen

import android.os.Handler
import kotlin.reflect.KProperty

abstract class ConfigObserver {
    val properties: HashSet<KProperty<*>>
    var handler: Handler? = null
        private set

    // 当传入的property为空时，表示监听该config下所有数据的变化
    // 响应时，全局监听会比指定key监听后收到消息
    // handler为空时，表示不指定线程，哪个线程触发的变化就在哪个线程执行回调
    constructor() {
        this.properties = HashSet()
        this.handler = null
    }

    constructor(handler: Handler? = null) {
        this.properties = HashSet()
        this.handler = handler
    }

    constructor(handler: Handler, vararg property: KProperty<*>) {
        this.properties = property.toHashSet()
        this.handler = handler
    }
    constructor(vararg property: KProperty<*>) {
        this.properties = property.toHashSet()
        this.handler = null
    }

    abstract fun onConfigChanged(key: KProperty<*>, oldValue: Any?, newValue: Any?)
}