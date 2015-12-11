package me.onez.androidmvp.model;

import me.onez.androidmvp.impl.OnResultListener;
import me.onez.androidmvp.model.entity.MainData;
import rx.Observable;

/**
 * Created by Vincent on 15/12/10.
 */
public interface MainViewModel {
  /**
   * 如果没有引入EventBus或者Rx,Otto之类的框架的话,
   * 当Model处理完数据之后加入回调,去完成对View的更新操作,让Presenter实现回调,
   * 这样Model和View就不会建立直接联系.
   */
  MainData loadData(String args, OnResultListener listener);
  ///////////////////////////////////////////////////////////////////////////
  // Rx Example Below
  ///////////////////////////////////////////////////////////////////////////
  /**
   * 如果加入了Rx系列,就会更方便处理了
   * @param args
   */
  Observable<MainData> loadDataRx(String args);
}
