package com.fxffxt.preferenceutils

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fxffxt.preferen.Config
import com.fxffxt.preferen.nullable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Config.ctx = this.application
        findViewById<View>(R.id.tv_text).setOnClickListener {
            log()
        }
    }
    private fun log() {
        val config = Student()
        config.a = listOf("333")
    }
}

class Student : Config {
    override val localFileName: String = "studentFileName"
    var a by nullable(listOf(""))
}