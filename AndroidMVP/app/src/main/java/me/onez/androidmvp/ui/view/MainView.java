package me.onez.androidmvp.ui.view;

import me.onez.androidmvp.model.entity.MainData;

/**
 * Created by Vincent on 15/12/10.
 */
public interface MainView {

  void showProgress();

  void updateUI(MainData data);

  void dimissProgress();

  void showError(String errorMsg);

}
