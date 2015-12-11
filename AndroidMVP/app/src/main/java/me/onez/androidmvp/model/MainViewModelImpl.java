package me.onez.androidmvp.model;

import me.onez.androidmvp.impl.OnResultListener;
import me.onez.androidmvp.model.entity.MainData;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 15/12/10.<br/>
 * Do real request logic in ViewModel,pass the result via EventBus or custom Callback
 */
public class MainViewModelImpl implements MainViewModel {

  @Override public MainData loadData(String args, OnResultListener listener) {
    // TODO: 15/12/10 here can do some request AND return result
    MainData result = null;
    try {
      //Do Request
      result = new MainData();
      listener.onSuccess(result);
    } catch (Exception e) {
      listener.onFail(e.getMessage());
    }
    listener.onFinish();
    return result;
  }

  ///////////////////////////////////////////////////////////////////////////
  // Use Rx ...
  @Override public Observable<MainData> loadDataRx(String args) {
    return Observable.create(new Observable.OnSubscribe<MainData>() {
      @Override public void call(Subscriber<? super MainData> subscriber) {
        // 请求数据等操作 eg:
        MainData result = null;
        try {
          Thread.sleep(3000);
          result = new MainData();
        } catch (InterruptedException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
        subscriber.onNext(result);
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io());
  }
  ///////////////////////////////////////////////////////////////////////////
}
