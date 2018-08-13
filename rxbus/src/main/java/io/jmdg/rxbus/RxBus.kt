package io.jmdg.rxbus

import android.os.Handler
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Joshua de Guzman on 16/07/2018.
 */

object RxBus {

    val subject = PublishSubject.create<Any>()
    val disposables = mutableMapOf<Any, CompositeDisposable>()

    /**
     * @param T - event data class
     * @param subscriber
     * @param observer
     */
    inline fun <reified T : Any> subscribe(subscriber: Any, noinline observer: (T) -> Unit) {
        val disposable = disposables[subscriber]
                ?: CompositeDisposable().apply { disposables[subscriber] = this }
        disposable.add(subject.ofType(T::class.java).subscribe(observer))
    }

    /**
     * @param subscriber - remove observer
     */
    fun unsubscribe(subscriber: Any) {
        disposables[subscriber]?.clear()
        disposables.remove(subscriber)
    }

    /**
     * @param subscriber - remove observer with exit callback
     */
    fun unsubscribe(subscriber: Any, callback: () -> Unit) {
        disposables[subscriber]?.clear()
        disposables.remove(subscriber)
        callback()
    }

    /**
     * @param subscriber - remove observer with exit callback
     */
    fun unsubscribe(subscriber: Any, callback: () -> Unit, delay: Long) {
        disposables[subscriber]?.clear()
        disposables.remove(subscriber)
        Handler().postDelayed(callback, delay)
    }

    /**
     * @param item - event emitted by the observer
     */
    fun post(item: Any) = subject.onNext(item)
}