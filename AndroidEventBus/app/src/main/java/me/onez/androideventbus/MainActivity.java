package me.onez.androideventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus3.0的基本使用
 * <li>不在限制接收事件名称xxxEvent,可以随便定义</li>
 * <li>通过注解@Subscribe配置 线程/sticky/优先权限</li>
 * <li>统一了register()方法,取消了registerSticky()</li>
 */
public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //EventBus.getDefault().post(new AnyEvent(111));
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Subscribe
  public void logit(AnyEvent event) {
    //默认情况下,在发送事件的线程中接收事件
    Log.d(TAG, "logit: " + event.id);
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void receiveThread(AnyEvent event) {
    //指定线程ThreadMode.BACKGROUND||ThreadMode.ASYNC||ThreadMode.MIAN||ThreadMode.POSTING
    Log.d(TAG, "receiveThread: " + Thread.currentThread().getName() + " ---- " + event.id);
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void handleSticky(AnyEvent event) {
    Log.d(TAG, "handleSticky: " + event.id);
  }
}
