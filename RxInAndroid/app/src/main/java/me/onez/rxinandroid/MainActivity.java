package me.onez.rxinandroid;

import android.os.Bundle;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseRxActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * 基本使用模式<BR/>
   * Observable.subscribe(Observer)
   */
  private void basicMode() {
    //完整回调的observer
    observable().subscribe(observer());

    //非完整回调
    // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
    observable().subscribe(onNextAction());
    // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
    observable().subscribe(onNextAction(), onErrorAction());
    // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
    observable().subscribe(onNextAction(), onErrorAction(), onCompletedAction());
  }

  /**
   * 线程调度
   */
  private void shedulerMode() {
    Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        // TODO: 15/12/11 in io Thread
        subscriber.onNext("1");
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
      @Override public void call() {
        // TODO: 15/12/11 in UI Thread
      }
    }).subscribeOn(AndroidSchedulers.mainThread()).map(new Func1<String, Integer>() {
      @Override public Integer call(String s) {
        return Integer.parseInt(s);
      }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
      @Override public void onCompleted() {
        Log.d(TAG, "onCompleted: ");
      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Integer integer) {
        Log.d(TAG, "onNext: " + integer.intValue());
      }
    });
  }

  //////////////////////////////////////
  //利用observable.compose(transformer)复用操作符
  private void composeMode() {
    Observable.just("2").map(new Func1<String, Integer>() {
      @Override public Integer call(String s) {
        return Integer.parseInt(s);
      }
    }).compose(transformer()).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        Log.d(TAG, "call: " + integer.intValue());
      }
    });

    Observable.from(new String[] { "1", "2", "0" }).map(new Func1<String, Integer>() {
      @Override public Integer call(String s) {
        return Integer.parseInt(s) + 10086;
      }
    }).compose(transformer()).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        Log.d(TAG, "call: " + integer.intValue());
      }
    });
  }

  /**
   * compose可以用来实现操作符的复用
   *
   * @return transformer
   */
  private Observable.Transformer<Integer, Integer> transformer() {
    return new Observable.Transformer<Integer, Integer>() {
      @Override public Observable<Integer> call(Observable<Integer> integerObservable) {
        return integerObservable.filter(new Func1<Integer, Boolean>() {
          @Override public Boolean call(Integer integer) {
            return integer.intValue() >= 1;
          }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }
}
