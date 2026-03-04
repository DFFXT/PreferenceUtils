package com.fxffxt.preferen

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DefaultObserverDispatcher: ConfigObserverDispatcher {
    private val observers = HashMap<String, MutableSet<ConfigObserver>>()
    private val globalObservers = HashSet<ConfigObserver>()

    /**
     * 添加监听器，不用后必须移除，否则内存泄漏
     */
    override fun addObserver(observer: ConfigObserver) {
        synchronized(observers) {
            for (key in observer.keys) {
                var observerSet = observers[key]
                if (observerSet == null) {
                    observerSet = mutableSetOf(observer)
                    observers[key] = observerSet
                }
                observerSet.add(observer)
            }
            if (observer.keys.isEmpty()) {
                globalObservers.add(observer)
            }
        }
    }

    override fun removeObserver(observer: ConfigObserver) {
        synchronized(observers) {
            for (key in observer.keys) {
                observers[key]?.remove(observer)
            }
            globalObservers.remove(observer)
        }
    }

    override fun dispatch(key: String, oldValue: Any?, newValue: Any?) {
        var tmpList: MutableList<ConfigObserver>? = null
        synchronized(observers) {
            val observerSet = observers[key]
            if (observerSet != null) {
                tmpList = LinkedList(observerSet)
            }
            if (globalObservers.isNotEmpty()) {
                tmpList = tmpList ?: LinkedList()
                tmpList.addAll(globalObservers)
            }
        }
        if (!tmpList.isNullOrEmpty()) {
            for (observer in tmpList) {
                if (observer.handler == null) {
                    observer.onConfigChanged(key, oldValue, newValue)
                } else {
                    observer.handler?.post {
                        observer.onConfigChanged(key, oldValue, newValue)
                    }
                }
            }
        }
    }
}