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
        val config = TestConfig()
        Log.i("log",config.propertyInt.toString())
        Log.i("log",config.propertyInt2.toString())
        Log.i("log",config.propertystring.toString())
        Log.i("log",config.propertystring2.toString())
        config.propertyInt = 100
        config.propertyInt2 = 200
        config.propertystring = "sss"
        config.propertystring2 = "ss22"
    }
}

class TestConfig:Config{
    override val localFileName: String
        get() = "testFile"
    var propertyInt by int(1)
    var propertyInt2 by intNullable()
    var propertystring by string("梦")
    var propertystring2 by stringNullable()
}