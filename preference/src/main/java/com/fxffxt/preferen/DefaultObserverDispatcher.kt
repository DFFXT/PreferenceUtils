package com.fxffxt.preferen

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DefaultObserverDispatcher: ConfigObserverDispatcher {
    private val observers = ConcurrentHashMap<String, MutableSet<ConfigObserver>>()
    override fun addObserver(observer: ConfigObserver) {
        synchronized(observers) {
            observers.computeIfAbsent(observer.key) { mutableSetOf() }.add(observer)
        }
    }

    override fun removeObserver(observer: ConfigObserver) {
        synchronized(observers) {
            observers[observer.key]?.remove(observer)
        }
    }

    override fun dispatch(key: String, oldValue: Any?, newValue: Any?) {
        var tmpList: List<ConfigObserver>? = null
        synchronized(observers) {
            val list = observers[key]
            if (list != null) {
                tmpList = LinkedList(list)
            }
        }
        if (tmpList != null) {
            for (observer in tmpList) {
                observer.onConfigChanged(key, oldValue, newValue)
            }
        }
    }
}