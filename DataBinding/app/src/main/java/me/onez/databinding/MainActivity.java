package me.onez.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import me.onez.databinding.databinding.MainBinding;
import me.onez.databinding.model.Coder;
import me.onez.databinding.model.CoderPro1;
import me.onez.databinding.model.CoderPro2;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final MainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    final Coder coder = new Coder();
    coder.setName("Vincent");
    coder.setGender("Male");
    coder.setInfo("Coder");
    coder.setActionName("Action Button");
    binding.setCoder(coder);

    final CoderPro1 coderPro1 = new CoderPro1();
    coderPro1.setName("Vincent pro1");
    coderPro1.setGender("Male pro1");
    coderPro1.setInfo("Coder pro1");
    binding.setCoderPro1(coderPro1);

    final CoderPro2 coderPro2 = new CoderPro2();
    coderPro2.actionName.set("Pro2 Action");
    binding.setCoderPro2(coderPro2);

    //甚至可以不用findViewById……
    binding.btnDo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //由于CoderPro2实现了Observable Binding,会自动更新UI
        coderPro2.actionName.set("UI Changed");
      }
    });

    binding.btnTest.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //由于Coder是单向绑定,一下设置,coder第一列不会更新UI
        coder.setName("New Vincent");
        coder.setGender("New Male");
        coder.setInfo("New Coder");
        coder.setActionName("New Action Button");

        //由于CoderPro1实现了Observable Binding,coderPro1所在列会自动更新UI
        coderPro1.setName("New Vincent pro1");
        coderPro1.setGender("New Male pro1");
        coderPro1.setInfo("New coder pro1");

      }
    });
  }
}
