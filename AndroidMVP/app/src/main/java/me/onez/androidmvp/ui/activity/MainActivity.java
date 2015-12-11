package me.onez.androidmvp.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import me.onez.androidmvp.R;
import me.onez.androidmvp.model.entity.MainData;
import me.onez.androidmvp.presenter.MainPresenter;
import me.onez.androidmvp.presenter.MainPresenterImpl;
import me.onez.androidmvp.ui.view.MainView;

/**
 * 作为View层实现相应View接口,持对应Presenter饮用,通过Presenter建立和Model之间的联系
 * 这样就可以解耦View和Model之间数据逻辑,View只需要关心负责如何显示,Model只需要关系如何去处理数据
 */
public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

  MainPresenter presenter;

  private TextView mResultTv;

  private Button mActionBtn;

  private ProgressDialog dialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    presenter = new MainPresenterImpl(this);
    mResultTv = (TextView) findViewById(R.id.tv_show);
    mActionBtn = (Button) findViewById(R.id.btn_go);
    dialog = new ProgressDialog(this);
    mActionBtn.setOnClickListener(this);
  }

  @Override public void showProgress() {
    dialog.show();
  }

  @Override public void updateUI(MainData data) {
    mResultTv.setText(data.getData());
  }

  @Override public void dimissProgress() {
    dialog.dismiss();
  }

  @Override public void showError(String errorMsg) {
    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
  }

  @Override public void onClick(View v) {
    if (v == mActionBtn) {
      presenter.loadData("params");
    }
  }
}
