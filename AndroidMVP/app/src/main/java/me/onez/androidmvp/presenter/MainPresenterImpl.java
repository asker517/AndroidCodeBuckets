package me.onez.androidmvp.presenter;

import me.onez.androidmvp.impl.OnResultListener;
import me.onez.androidmvp.model.MainViewModel;
import me.onez.androidmvp.model.MainViewModelImpl;
import me.onez.androidmvp.model.entity.MainData;
import me.onez.androidmvp.ui.view.MainView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Vincent on 15/12/10.
 */
public class MainPresenterImpl implements MainPresenter, OnResultListener {

  private MainView view;
  private MainViewModel viewModel;

  public MainPresenterImpl(MainView view) {
    this.view = view;
    viewModel = new MainViewModelImpl();
  }

  @Override public void loadData(String args) {
    view.showProgress();
    viewModel.loadData(args, this);
  }

  @Override public void onSuccess(Object data) {
    view.updateUI((MainData) data);
  }

  @Override public void onFail(String errorMsg) {
    view.showError(errorMsg);
  }

  @Override public void onFinish() {
    view.dimissProgress();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Rx 如果饮用了Rx那么就不需额外实现回调接口(OnResultListener),通过Subcriber自身回调即可处理
  @Override public void loadDataRx(String args) {
    view.showProgress();
    viewModel.loadDataRx(args)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MainData>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            view.dimissProgress();
            view.showError(e.getMessage());
          }

          @Override public void onNext(MainData data) {
            view.dimissProgress();
            view.updateUI(data);
          }
        });
  }
  ///////////////////////////////////////////////////////////////////////////
}
