package me.onez.androidmvp.impl;

/**
 * Created by Vincent on 15/12/10.
 * Can be replaced by EventBus Or Rx
 */
public interface OnResultListener {
  void onSuccess(Object data);

  void onFail(String errorMsg);

  void onFinish();
}
