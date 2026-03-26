package com.fxffxt.preferen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty


abstract class ObservableConfig : Config(), ConfigObserverDispatcher by DefaultObserverDispatcher() {




    // flow观测
    internal val flows = HashMap<String, MutableStateFlow<Any?>>()

    private val defaultObserver = object : ConfigObserver() {
        override fun onConfigChanged(
            key: KProperty<*>,
            oldValue: Any?,
            newValue: Any?
        ) {
            if (oldValue != newValue) {
                flows[key.name]?.value = newValue
            }
        }
    }


    init {
        addObserver(defaultObserver)
    }

    override fun deleteAll() {
        val oldMap = HashMap(getSharedPreference().all)
        super.deleteAll()
        for ((property, def) in keyDef) {
            val old = oldMap[keysMap[property]] ?: def
            if (old != def) {
                this.dispatch(property, old, def)
            }
        }
    }



    fun <T> stateFlowOfNullable(property: KProperty<*>): StateFlow<T?> {
        if (flows[property.name] == null) {
            synchronized(this) {
                if (flows[property.name] == null) {
                    flows[property.name] = MutableStateFlow(getValue<T?>(property = property))
                }
            }
        }
        return flows[property.name]!! as StateFlow<T?>
    }

    fun <T> stateFlowOfNoneNull(property: KProperty<*>): StateFlow<T> {
        if (flows[property.name] == null) {
            synchronized(this) {
                if (flows[property.name] == null) {
                    flows[property.name] = MutableStateFlow(getValue<T>(property = property))
                }
            }
        }
        return flows[property.name]!! as StateFlow<T>
    }


}