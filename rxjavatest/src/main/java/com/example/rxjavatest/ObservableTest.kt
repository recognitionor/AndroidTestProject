package com.example.rxjavatest

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import org.reactivestreams.Publisher
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ObservableTest {

    private fun print(str: String) {
        Log.d("jhlee", "str : $str")
    }

    fun observableCreate() {
        val source: Observable<String> = Observable.create { emitter ->
            emitter.onNext("Hello")
            emitter.onNext("Yena")
            emitter.onComplete()
        }
        source.subscribe {
            print("subscribe : $it")
        }
    }

    fun observableCreateComplete() {
        val source: Observable<String> = Observable.create { emitter ->
            emitter.onNext("Hello")
            emitter.onComplete();
            emitter.onNext("Yena")
        }
        source.subscribe {
            print("subscribe : $it")
        }
    }

    fun observableCreateError() {
        val source: Observable<String> = Observable.create { emitter ->
            emitter.onNext("Hello")
            emitter.onError(Throwable())
            emitter.onNext("Yena")
        }
        source.subscribe(
            { s: String? -> println(s) }
        ) {
            println("Good bye")
        }
    }

    fun observableJust() {
        val source = Observable.just("Hello", "Yena")
        source.subscribe {
            print("observableJust : $it")
        }
    }

    fun fromArray() {
        val itemArray = arrayOf("Morning", "Afternoon", "Evening")
        val source: Observable<*> = Observable.fromArray(*itemArray)
        source.subscribe {
            print("fromArray : $it")
        }
    }

    fun fromIterable() {
        val itemList = ArrayList<String>()
        itemList.add("Morning")
        itemList.add("Afternoon")
        itemList.add("Evening")
        val source: Observable<*> = Observable.fromIterable<Any>(itemList)
        source.subscribe {
            print("fromIterable : $it")
        }
    }

    fun fromFuture() {
        val future = Executors.newSingleThreadExecutor()
            .submit<String> {
                Thread.sleep(5000)
                "This is the future"
            }
        val source: Observable<*> = Observable.fromFuture(future)
        source.subscribe {
            print("fromFuture : $it")
        }
    }

    fun fromPublisher() {
        val publisher: Publisher<String> = Publisher { subscriber ->
            subscriber.onNext("Morning")
            subscriber.onNext("Afternoon")
            subscriber.onNext("Evening")
            subscriber.onComplete()
            subscriber.onNext("Messi")
        }
        val source: Observable<String> = Observable.fromPublisher(publisher)
        source.subscribe {
            print("fromPublisher : $it")
        }
    }

    fun fromCallable() {
        val callable: Callable<String> = Callable<String> { "RxJava is cool" }
        val source = Observable.fromCallable(callable)
        source.subscribe {
            print("fromCallable : $it")
        }
    }

    fun single() {
        Single.create { emitter: SingleEmitter<Any?> ->
            emitter.onSuccess(
                "Hello"
            )
        }.subscribe { o: Any? -> print("single :  $o") }
    }

    fun completable() {
        Completable.create { emitter ->
            {
                print("completable OK")
                emitter.onComplete()
            }
        }.subscribe {
            print("Completed")
        }
    }

    fun coldObservable() {
        val src: Observable<*> = Observable.interval(1, TimeUnit.SECONDS)
        src.subscribe { value: Any -> print("First: $value") }
        Thread.sleep(3000)
        src.subscribe { value: Any -> print("Second: $value") }
        Thread.sleep(3000)
    }
}