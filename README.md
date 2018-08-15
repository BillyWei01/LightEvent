# LigntEvent
A lightweight publish/subscribe event bus for Android.
Code of LigntEvent is extremely simple, just less then fifty lines.
But it works well.

# Implement
```kotlin
interface Observer {
    fun onEvent(event: Int, vararg args : Any?)
    fun listEvents(): IntArray
}
```

```kotlin
object EventManager {
    private val handler = Handler(Looper.getMainLooper())
    private val observers = SparseArray<LinkedList<Observer>>(16)

    @JvmStatic
    @Synchronized
    fun register(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            var list = observers.get(event)
            if (list == null) {
                list = LinkedList()
                observers.put(event, list)
            }
            if (observer !in list) {
                list.add(observer)
            }
        }
    }

    @JvmStatic
    @Synchronized
    fun unregister(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            observers.get(event)?.removeLastOccurrence(observer)
        }
    }

    @JvmStatic
    @Synchronized
    fun notify(event: Int, vararg args: Any?) {
        observers.get(event)?.forEach { observer ->
            handler.post { observer.onEvent(event, *args) }
        }
    }
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