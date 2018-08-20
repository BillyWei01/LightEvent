# LightEvent
[ ![Download](https://api.bintray.com/packages/horizon757/maven/LightEvent/images/download.svg) ](https://bintray.com/horizon757/maven/LightEvent/_latestVersion)

LightEvent is a lightweight publish/subscribe event bus for Android.
Code of LightEvent is extremely simple, just less then half a hundred lines, and it works well.

# Download
```gradle
repositories {
    jcenter()
}

dependencies {
    implementation  'com.horizon.event:event:1.0.1'
}
```

# How to use

## 1、Declare Events
```kotlin
object Events {
    const val LOGIN = 1
    const val LOGOUT = 2
}
```

## 2、Register & Unregister
```kotlin
abstract class BaseActivity : AppCompatActivity(), Observer {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventManager.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

    override fun onEvent(event: Int, vararg args: Any?) {
    }

    override fun listEvents(): IntArray {
        return IntArray(0)
    }
}
```

## 3、Subscribe Events
```kotlin
class MainActivity : BaseActivity() {
    private fun setViews(account: String?) {
        // update views
    }

    override fun listEvents(): IntArray {
        return intArrayOf(Events.LOGIN, Events.LOGOUT)
    }

    override fun onEvent(event: Int, vararg args: Any?) {
        when (event) {
            Events.LOGIN -> setViews(args[0] as String?)
            Events.LOGOUT -> setViews(null)
        }
    }
}
```

## 4、Publish Event
```kotlin
object AccountManager {
    fun login(account: String, password: String) {
        // process login
        EventManager.notify(Events.LOGIN, account)
    }

    fun logout() {
        // process logout
        EventManager.notify(Events.LOGOUT)
    }
}
```


# Link
For more detail, see:
https://www.jianshu.com/p/bfce80e16141

# License
See the [LICENSE](LICENSE.md) file for license rights and limitations.

