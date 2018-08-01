package com.xrojan.rxbus

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
     * @param subscriber - registered observer
     */
    fun unsubscribe(subscriber: Any) {
        disposables[subscriber]?.clear()
        disposables.remove(subscriber)
    }

    /**
     * @param item - event emitted by the observer
     */
    fun post(item: Any) = subject.onNext(item)
}