package me.onez.rxinandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseRxActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //useFlatMap();
        //useConcatMap();
        //doOnNextThreadTest();
        //onErrorHandleTest();
      }
    });
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
    // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError
    // () 和 onCompleted()
    observable().subscribe(onNextAction(), onErrorAction(), onCompletedAction());
  }

  /**
   * 线程调度
   */
  private void shedulerMode() {
    Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        // TODO: 15/12/11 in io Thread
        subscriber.onNext("1");
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
      @Override
      public void call() {
        // TODO: 15/12/11 in UI Thread
      }
    }).subscribeOn(AndroidSchedulers.mainThread()).map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        return Integer.parseInt(s);
      }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
      @Override
      public void onCompleted() {
        Log.d(TAG, "onCompleted: ");
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "onNext: " + integer.intValue());
      }
    });
  }

  //////////////////////////////////////
  //利用observable.compose(transformer)复用操作符
  private void composeMode() {
    Observable.just("2").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        return Integer.parseInt(s);
      }
    }).compose(transformer()).subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer integer) {
        Log.d(TAG, "call: " + integer.intValue());
      }
    });

    Observable.from(new String[] { "1", "2", "0" }).map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        return Integer.parseInt(s) + 10086;
      }
    }).compose(transformer()).subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer integer) {
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
      @Override
      public Observable<Integer> call(Observable<Integer> integerObservable) {
        return integerObservable.filter(new Func1<Integer, Boolean>() {
          @Override
          public Boolean call(Integer integer) {
            return integer.intValue() >= 1;
          }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * Merge操作符将多个Observable发射的数据整合起来发射，
   * 就如同是一个Observable发射的数据一样。发射数据是无序的
   */
  private void useMerge() {
    Observable.merge(observable(), observable())
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Observer<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {

          }
        });
  }

  /**
   * Concat操作符将多个Observable发射的数据整合起来发射，
   * 就如同是一个Observable发射的数据一样。发射数据是有序的
   */
  private void useConcat() {
    Observable.concat(observable(), observable())
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Observer<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {

          }
        });
  }

  private List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

  /**
   * flatMap得到最终结果是无序的
   */
  private void useFlatMap() {
    Observable.from(nums).flatMap(new Func1<Integer, Observable<Integer>>() {
      @Override
      public Observable<Integer> call(Integer integer) {
        return Observable.just(integer.intValue() * integer.intValue())
            .subscribeOn(Schedulers.io());
      }
    }).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "onNext: " + integer);
      }
    });
  }

  /**
   * concatMap得到的最终结果是有序的
   */
  private void useConcatMap() {
    Observable.from(nums).concatMap(new Func1<Integer, Observable<Integer>>() {
      @Override
      public Observable<Integer> call(Integer integer) {
        return Observable.just(integer.intValue() * integer.intValue())
            .subscribeOn(Schedulers.io());
      }
    }).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "onNext: " + integer.intValue());
      }
    });
  }

  /**
   * doOnNext线程测试: call0和call1会受到subscribeOn的线程影响,
   * call2受到observeOn的线程影响,切换到新的线程,
   * call3继续利用observeOn变幻线程,切换到主线程.
   */
  private void doOnNextThreadTest() {
    observable().doOnNext(new Action1<String>() {
      @Override
      public void call(String s) {
        //RxCachedThreadScheduler-2
        Log.d(TAG, "call0: " + Thread.currentThread().getName());
      }
    }).subscribeOn(Schedulers.io()).doOnNext(new Action1<String>() {
      @Override
      public void call(String s) {
        //RxCachedThreadScheduler-2
        Log.d(TAG, "call1: " + Thread.currentThread().getName());
      }
    }).observeOn(Schedulers.io()).doOnNext(new Action1<String>() {
      @Override
      public void call(String s) {
        //RxCachedThreadScheduler-1
        Log.d(TAG, "call2: " + Thread.currentThread().getName());
      }
    }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<String>() {
      @Override
      public void call(String s) {
        //main
        Log.d(TAG, "call3: " + Thread.currentThread().getName());
      }
    }).subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(String s) {
        //main
        Log.d(TAG, "call4: " + Thread.currentThread().getName());
      }
    });
  }

  /**
   * 当调用onErrorReturn时,当error发生,onErrorReturn会接受到异常,
   * 可以在这了定义预发射的数据继续发射
   */
  private void onErrorHandleTest() {
    Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 10; i++) {
          if (i < 5) {
            subscriber.onNext(i);
          } else {
            //当数据大于5时,触发错误,但是onErrorReturn只会触发一次
            Log.d(TAG, "on Error call: ");//这里会调用多次
            subscriber.onError(new Throwable("Error Happens"));
          }
        }
      }
    }).onErrorReturn(new Func1<Throwable, Integer>() {
      @Override
      public Integer call(Throwable throwable) {
        //当onError后,触发onErrorReturn,这里可以预先定义发射数据,eg.发射元素 10
        //然后正常终止,只会首次触发
        Log.d(TAG, "onErrorReturn call: ");
        return 10;
      }
    }).filter(new Func1<Integer, Boolean>() {
      @Override
      public Boolean call(Integer integer) {
        if (integer.intValue() > 9) {
          //onErrorReturn发射的数据接下来的操作符会正常处理
          Log.d(TAG, "filter call: " + integer.intValue());
          return false;
        }
        return true;
      }
    }).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        Log.d(TAG, "onCompleted: ");
      }

      @Override
      public void onError(Throwable e) {
        Log.d(TAG, "onError: " + e.getMessage());
      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "onNext: " + integer.intValue());
      }
    });
  }
}
