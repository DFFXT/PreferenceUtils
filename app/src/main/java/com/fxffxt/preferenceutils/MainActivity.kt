package com.fxffxt.preferenceutils

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fxffxt.preferen.ConfigObserver
import com.fxffxt.preferen.ObservableConfig
import com.fxffxt.preferen.nullable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv_text).setOnClickListener {
            log()
        }
        Student.addObserver(object : ConfigObserver(Student::b) {
            override fun onConfigChanged(
                key: String,
                oldValue: Any?,
                newValue: Any?
            ) {
                Log.i("sssf","------->key:$key,oldValue:$oldValue,newValue:$newValue")
            }
        })
    }
    private fun log() {
        val config = Student
        config.a = listOf("333")
        config.b += "? "
        Student.deleteAll()
    }
}

object Student : ObservableConfig() {
    override val localFileName: String = "studentFileName"
    var a by nullable(listOf(""))
    var b by nullable("defstr")
}