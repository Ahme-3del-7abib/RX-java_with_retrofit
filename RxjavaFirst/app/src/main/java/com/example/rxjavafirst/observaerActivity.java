package com.example.rxjavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class observaerActivity extends AppCompatActivity {

    private static final String TAG = "observaerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observaer);

        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(c -> Log.d(TAG, "UpStream : " + c + "Current Tread : " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .subscribe(o -> Log.d(TAG, "DownStream : " + o + "Current Tread : " + Thread.currentThread().getName()));

    }

    private void passOvserverToObsevableUsingCreate() {

        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                for (int i = 0; i < 5; i++) {
                    emitter.onNext(i);

                }
                emitter.onComplete();
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Object o) {

                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.d(TAG, "Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        observable.subscribe(observer);
    }

    private void passOvserverToObsevableUsingArray() {

        int[] list = new int[5];
        list[0] = 1;
        list[1] = 2;
        list[2] = 3;
        list[3] = 4;
        list[4] = 5;
        Observable observable = Observable.fromArray(list);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Object o) {

                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.d(TAG, "Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        observable.subscribe(observer);
    }
}