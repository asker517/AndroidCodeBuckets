package me.onez.rxinandroid;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Vincent on 15/12/11.
 */
public class BaseRxActivity extends AppCompatActivity {

  protected static final String TAG = "BaseRxActivity";

  /**
   * 创建一个Observable 被观察者
   *
   * @return Observable<String>
   */
  protected Observable<String> observable() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("String result");
        //subscriber.onNext("Another String result");
        //subscriber.onError();
        subscriber.onCompleted();
      }
    });
  }

  /**
   * 创建一个观察者/订阅者
   *
   * @return Subscriber, <b></>Subscriber 实现了Observer接口</b>,实现完整回调
   */
  protected Subscriber<String> observer() {
    return new Subscriber<String>() {
      @Override
      public void onCompleted() {
        Log.d(TAG, "onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        Log.d(TAG, "onError: " + e.getMessage());
      }

      @Override
      public void onNext(String s) {
        Log.d(TAG, "onNext: " + s);
      }
    };
  }

  ///////////////////////////////////////////////////////////////////////////
  //自定义observer不完整回调
  protected Action1<String> onNextAction() {
    return new Action1<String>() {
      // onNext()
      @Override
      public void call(String s) {
        Log.d(TAG, s);
      }
    };
  }

  protected Action1<Throwable> onErrorAction() {
    return new Action1<Throwable>() {
      // onError()
      @Override
      public void call(Throwable throwable) {
        // Error handling
      }
    };
  }

  protected Action0 onCompletedAction() {
    return new Action0() {
      // onCompleted()
      @Override
      public void call() {
        Log.d(TAG, "onCompleted");
      }
    };
  }
  ///////////////////////////////////////////////////////////////////////////
}
