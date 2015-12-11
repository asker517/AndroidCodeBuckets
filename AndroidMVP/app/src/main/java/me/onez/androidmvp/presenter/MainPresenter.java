package me.onez.androidmvp.presenter;

/**
 * Created by Vincent on 15/12/10.
 * 作为View和Model沟通的中介桥梁
 */
public interface MainPresenter {

  void loadData(String args);

  ///////////////////////////////////////////////////////////////////////////
  // Rx load data
  void loadDataRx(String args);
  ///////////////////////////////////////////////////////////////////////////
}
