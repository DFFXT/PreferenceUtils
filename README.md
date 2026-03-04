# PreferenceUtils
适用于android kotlin项目
代理方式快捷使用SharePreference，以类为单位，方便使用 ，支持默认值、变更监听

# 添加依赖
```aiignore
allprojects {
    repositories {
         ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.DFFXT:PreferenceUtils:1.2.9'
}
```



# 使用方法
## 声明一个普通Preference文件
```aiignore
/** 继承Config接口即可 **/
object Student : Config {
    override val localFileName: String = "studentFileName"//文件名称
    var name by noneNull(def = "defaultName")
    var age by noneNull(def = 16)
    var height by noneNull(def = 170.0f)
}
    
/** 使用 **/
val config = Student()
config.height += 0.23f
print(config.name)
print(config.age)
print(config.height)
```

## 声明一个可以监听的Preference配置
```aiignore
object Student : ObservableConfig() {
    override val localFileName: String = "studentFileName"//文件名称
    var name by noneNull(def = "defaultName")
    var age by noneNull(def = 16)
    var height by noneNull(def = 170.0f)
}

/** 监听，属性、线程可选 **/
Student.addObserver(object : ConfigObserver(Handler(Looper.getMainLooper()), Student::name, Student::age) {
    override fun onConfigChanged(
        key: String,
        oldValue: Any?,
        newValue: Any?
    ) {
        Log.i("log","------->key:$key,oldValue:$oldValue,newValue:$newValue")
    }
})

```
    
