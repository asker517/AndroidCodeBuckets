package me.onez.androideventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);

    EventBus.getDefault().postSticky(new AnyEvent(2222));
  }
}
