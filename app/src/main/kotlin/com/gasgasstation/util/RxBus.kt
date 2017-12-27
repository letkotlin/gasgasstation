package com.gasgasstation.util

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by kws on 2017. 12. 27..
 */
object RxBus {

    private val subjectMap = HashMap<String, PublishSubject<Any>>()
    private val compositDisposableMap = HashMap<Any, CompositeDisposable>()

    private fun getSubject(subjectKey: String): PublishSubject<Any> {
        var subject: PublishSubject<Any>? = subjectMap[subjectKey]
        if (subject == null) {
            subject = PublishSubject.create()
            subject!!.subscribeOn(AndroidSchedulers.mainThread())
            subjectMap.put(subjectKey, subject)
        }

        return subject
    }

    private fun getCompositeDisposable(`object`: Any): CompositeDisposable {
        var compositeDisposable: CompositeDisposable? = compositDisposableMap[`object`]
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            compositDisposableMap.put(`object`, compositeDisposable)
        }

        return compositeDisposable
    }

    fun subscribe(subject: String, lifecycle: Any, action: Consumer<Any>) {
        val disposable = getSubject(subject).subscribe(action)
        getCompositeDisposable(lifecycle).add(disposable)
    }

    fun unregister(lifecycle: Any) {
        val compositeDisposable = compositDisposableMap.remove(lifecycle)
        compositeDisposable?.clear()
    }

    fun publish(dispose: String, message: Any) {
        getSubject(dispose).onNext(message)
    }
}