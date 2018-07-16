# rxbus
A simplified EventBus wrapper on top of Kotlin and Rx

[![Release](https://jitpack.io/v/joshuadeguzman/rxbus.svg)](https://jitpack.io/#joshuadeguzman/rxbus)



### Features

| Methods                           | Usage                                                   |
| -                                 | -                                                       |
| subscribe<Event>(subscriber)      | Register type object as an observer                     |
| post(event: Any)                  | Passing event class to be ommitted on the observer      |
| unsubscribe(subscriber: Any)      | Removes registered observers from the disposables       |

### Installation
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.joshuadeguzman:rxbus:v0.0.1'
}
```

### Create Events
```kotlin
// Events.kt
// Define your event subscription here

// Passing data
data class ShowDataEvent(val data: String)

// No passing of data
class NoDataEvent
```

### Implement methods
```kotlin
// Activity.kt
// This defines subscriptions (eg. on onCreate)

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    
    // Register observers
    rxbus.subscribe<ShowDataEvent>(this) {
        Log.e(tag, it.data)
    }

    rxbus.subscribe<NoDataEvent>(this) {
        callSomeMethod()
    }
}

// Remove subscriptions
override fun onStop() {
    super.onStop()
    rxbus.unsubscribe(this)
}

override fun onDestroy() {
    super.onDestroy()
    rxbus.unsubscribe(this)
}
```

```kotlin
// Fragment.kt
// This sends data to event observers/subscribers

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_fragment)
    
    bt_send_data.setOnClickListener {
        rxbus.post(ShowDataEvent("Hi, this is from Fragment.kt")
    }
}
```
