package com.example.rxjavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class observableActivity extends AppCompatActivity {

    private static final String TAG = "observableActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
    }


    /*  cold observable  */
    private void coldObservable() {

        // After 5 iteration add new data to observable without delay
        coldObservable();

        Observable<Long> cold = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS);
        cold.subscribe(i -> Log.d(TAG, "onCreate: Student 1" + i));

        // After 3 second add new student
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cold.subscribe(i -> Log.d(TAG, "onCreate: Student 2" + i));
    }


    /*     hot observable with Connectable Observable    */

    private void hotObservableWithConnectable() {

        ConnectableObservable<Long> hot =
                ConnectableObservable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS).publish();
        hot.connect();

        hot.subscribe(i -> Log.d(TAG, "onCreate: Student 1" + i));

        // After 3 second add new student
        sleep(3000);

        hot.subscribe(i -> Log.d(TAG, "onCreate: Student 2" + i));
    }

      /*
        Hot observable with Subject type
          1 -- publish subject
          2 -- behavior subject
          3 -- replay subject
          4 -- Async subject
     */

    private void hotPublishSubject() {


        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 "));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
    }

    private void hotBehaviourSubject() {

        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 "));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");

    }

    private void hotReplaySubject() {

        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 "));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
    }

    private void hotAsyncObservable() {

        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 "));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "OnCreate : Student 1 " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
        subject.onComplete();
    }


    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}