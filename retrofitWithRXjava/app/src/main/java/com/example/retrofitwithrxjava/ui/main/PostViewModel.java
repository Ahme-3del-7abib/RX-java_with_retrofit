package com.example.retrofitwithrxjava.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofitwithrxjava.data.PostsClient;
import com.example.retrofitwithrxjava.pojo.PostModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";
    MutableLiveData<List<PostModel>> postsMutableLiveData = new MutableLiveData<>();
    CompositeDisposable disposable = new CompositeDisposable();

    public void getPosts() {

        // result return from retrofit pass it to observable
        Observable observable = PostsClient.getINSTANCE().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable.add(observable.subscribe(c -> postsMutableLiveData.setValue((List<PostModel>) c),
                o -> Log.d(TAG, "Error" + o)));

        //Observer<List<PostModel>> observer = new Observer<List<PostModel>>() {};
        // observable.subscribe(observer);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}