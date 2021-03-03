# PreferenceUtils
代理方式快捷使用SharePreference

# 添加依赖
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  
    dependencies {
	        implementation 'com.github.DFFXT:PreferenceUtils:1.1'
	}
  
  
# 使用方法
## 声明一个本地Preference文件
    //继承Config接口即可
    class Student : Config {
      override val localFileName: String = "studentFileName"//文件名称
      var name by noneNull(def = "defaultName")
      var age by noneNull(def = 16)
      var height by noneNull(def = 170.0f)
    }
    
    //使用
    
    Config.ctx = this//初始化，一般放在Application onCreate里面初始化
    val config = Student()
    config.height += 0.23f
    print(config.name)
    print(config.age)
    print(config.height)
    
