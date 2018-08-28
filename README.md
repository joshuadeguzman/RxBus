# RxBus
A simplified event-driven library on top of Kotlin and Rx

[![Kotlin](https://img.shields.io/badge/Kotlin-1.2.51-green.svg?style=flat-square)](http://kotlinlang.org)
[![RxJava](https://img.shields.io/badge/Support-27.1.1-6ab344.svg?style=flat-square)](https://github.com/ReactiveX/RxJava/releases/tag/v2.1.10)
[![Build Status](https://img.shields.io/travis/joshuadeguzman/RxBus.svg?style=flat-square)](https://travis-ci.org/joshuadeguzman/RxBus)
[![GitHub (pre-)release](https://img.shields.io/github/release/joshuadeguzman/rxbus/all.svg?style=flat-square)
](./../../releases)

### Features

| Methods                           | Usage                                                   |
| -                                 | -                                                       |
| subscribe`<Event>`(subscriber)      | Register type object as an observer                     |
| post(event: Any)                  | Passing event class to be ommitted on the observer      |
| unsubscribe(subscriber: Any)      | Removes registered observers from the disposables       |

### Installation
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    // Replace version with release version, e.g. 1.0.0-alpha, -SNAPSHOT
    implementation 'io.jmdg:rxbus:[VERSION]'
}
```

### Create Events
```kotlin
// Events.kt
// Define your events here

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
    RxBus.subscribe<ShowDataEvent>(this) {
        Log.e(tag, it.data)
    }

    RxBus.subscribe<NoDataEvent>(this) {
        callSomeMethod()
    }
}

// Remove subscriptions
override fun onStop() {
    super.onStop()
    RxBus.unsubscribe(this)
}

override fun onDestroy() {
    super.onDestroy()
    RxBus.unsubscribe(this)
}
```

```kotlin
// Fragment.kt
// This sends data to event observers/subscribers

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_fragment)
    
    bt_send_data.setOnClickListener {
        RxBus.post(ShowDataEvent("Hi, this is from Fragment.kt"))
    }
}
```
