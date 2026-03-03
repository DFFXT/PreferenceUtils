package com.fxffxt.preferen

abstract class ObservableConfig: Config, ConfigObserverDispatcher by DefaultObserverDispatcher() {

    // 各个属性的默认值map
    internal val keyDef = HashMap<String, Any?>()

    override fun delete(key: String) {
        val old = getSharedPreference().all[key] ?: keyDef[key]
        super.delete(key)
        val new = keyDef[key]
        if (old != new) {
            this.dispatch(key, old, new)
        }
    }

    override fun deleteAll() {
        val oldMap = HashMap(getSharedPreference().all)
        super.deleteAll()
        for ((key, value) in oldMap) {
            val old = value ?: keyDef[key]
            val new = keyDef[key]
            if (old != new) {
                this.dispatch(key, old, new)
            }
        }
    }
}