package com.fxffxt.preferenceutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fxffxt.preferen.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Config.ctx = this
        val config = Student()
        config.height += 0.23f
        print(config.name)
        print(config.age)
        print(config.height)
    }
}


class Student : Config {
    override val localFileName: String = "studentFileName"
    var name by string("defaultName")
    var age by int(16)
    var height by float(170.0f)
}