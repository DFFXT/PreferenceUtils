package com.fxffxt.preferenceutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fxffxt.preferen.*
import org.json.JSONArray
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Config.ctx = this
        val config = Student()
        /*config.f = F().apply {
            tt = 996
        }
        config.json = A().apply {
            tt = 987
        }*/
        config.height += 0.23f
        log()

    }
    private fun log(){
        val config = Student()
        print(config.name)
        print(config.age)
        print(config.height)
        print(config.f.tt)
        print(config.json.tt)
    }
}

class F:Serializable{
    var tt = 123
}
class A{
    var tt = 44
}

class Student : Config {
    override val localFileName: String = "studentFileName"
    var name by noneNull("defaultName")
    var age by noneNull(16)
    var height by noneNull(170.0f)
    var f:F by noneNull(F())
    var json:A by nullable(A())
}