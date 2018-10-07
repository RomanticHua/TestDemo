package com.example.a10616.testdemo;


import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class Rxjava {

    @SuppressLint("CheckResult")
    public void test() {
        ObservableOnSubscribe<Integer> source = new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onComplete();
                emitter.onError(new Exception("我是错误!"));
            }
        };

        ObservableEmitter<Integer> emitter;
//        source.subscribe(emitter);


        // ObservableCreate -> ObservableMap
        // ObservableMap 订阅 Observer   ->  ObservableCreate 订阅   MapObserver       源码: source.subscribe(new MapObserver<T, U>(t, function));
        // 执行MapObservable的onNext ,onError, onComplete 方法  , 在OnNext方法中执行转变操作.

        Observable.interval(0,5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {

                    }
                });

        Observable.create(source)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return null;
                    }
                })
                .flatMap(new Function<String, ObservableSource<Student>>() {
                    @Override
                    public ObservableSource<Student> apply(String s) {
                        return Observable.just(new Student(s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.single())
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) {

                    }
                });

        Observable<Integer> integerObservable = Observable.create(source);
        Observable.create(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer student) {

                    }
                });
    }
}
