package com.example.rxjavafirst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.rxjavafirst.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

                binding.editTxT.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() != 0) {
                            emitter.onNext(s);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }).doOnNext(o -> Log.d(TAG, "upStream : " + o))
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        return Integer.parseInt(o.toString()) * 2;
                    }
                })
                .debounce(2, TimeUnit.SECONDS)
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Throwable {
                        return sendToApi(o.toString());
                    }
                })
                .distinctUntilChanged()
                .filter(c -> !(c.toString().equals("Ahmed")))
                .subscribe(c -> {
                    Log.d(TAG, "DownStream : " + c);
                });
    }

    public Observable sendToApi(String data) {

        Observable observable = Observable.just("Send data to api " + data);
        observable.subscribe(c -> Log.d(TAG, "send To Api : " + c));
        return observable;
    }
}