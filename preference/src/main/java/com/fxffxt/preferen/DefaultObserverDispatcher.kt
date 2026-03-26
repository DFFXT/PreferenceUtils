package com.fxffxt.preferen

import java.util.*

class DefaultObserverDispatcher: ConfigObserverDispatcher {
    private val observers = HashMap<String, MutableSet<ConfigObserver>>()
    private val globalObservers = HashSet<ConfigObserver>()

    /**
     * 添加监听器，不用后必须移除，否则内存泄漏
     */
    override fun addObserver(observer: ConfigObserver) {
        synchronized(observers) {
            for (key in observer.propertiesName) {
                var observerSet = observers[key]
                if (observerSet == null) {
                    observerSet = mutableSetOf(observer)
                    observers[key] = observerSet
                }
                observerSet.add(observer)
            }
            if (observer.propertiesName.isEmpty()) {
                globalObservers.add(observer)
            }
        }
    }

    override fun removeObserver(observer: ConfigObserver) {
        synchronized(observers) {
            for (key in observer.propertiesName) {
                observers[key]?.remove(observer)
            }
            globalObservers.remove(observer)
        }
    }

    override fun dispatch(propertyName: String, oldValue: Any?, newValue: Any?) {
        var tmpList: MutableList<ConfigObserver>? = null
        synchronized(observers) {
            val observerSet = observers[propertyName]
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
                    observer.onConfigChanged(propertyName, oldValue, newValue)
                } else {
                    observer.handler?.post {
                        observer.onConfigChanged(propertyName, oldValue, newValue)
                    }
                }
            }
        }
    }
}