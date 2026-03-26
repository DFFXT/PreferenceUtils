package com.fxffxt.preferenceutils

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fxffxt.preferen.ConfigObserver
import com.fxffxt.preferen.ObservableConfig
import com.fxffxt.preferen.nullable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv_text).setOnClickListener {
            log()
        }
        Student.addObserver(object : ConfigObserver(Handler(Looper.getMainLooper()), Student::a, Student::b) {
            override fun onConfigChanged(
                key: KProperty<*>,
                oldValue: Any?,
                newValue: Any?
            ) {
                Log.i("log","------->key:$key,oldValue:$oldValue,newValue:$newValue")
            }
        })
        GlobalScope.launch{
            Student.stateFlowOfNullable<String>(Student::b).collect {
                Log.i("log-->", "collect b:${it}")
            }
        }

    }
    private fun log() {
        val config = Student
        config.a = listOf("333")
        config.b += "? "
        Log.i("log", "a:${config.a},b:${config.b},c:${config.c}")
        Student.deleteAll()
    }
}

object Student : ObservableConfig() {
    override val localFileName: String = "studentFileName"
    var a by nullable(listOf(""))
    var b by nullable("defstr")

    var c: Int? by nullable()
}