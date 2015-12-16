package me.onez.databinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import me.onez.databinding.BR;

/**
 * Created by Vincent on 15/12/10.
 * Observable Binding方式2
 */
public class CoderPro2 {

  private static final String TAG = "CoderPro2";

  public ObservableField<String> name = new ObservableField<>();
  public ObservableField<String> gender = new ObservableField<>();
  public ObservableField<String> info = new ObservableField<>();
  public ObservableField<String> actionName = new ObservableField<>();

  public View.OnClickListener onClickListener(){
    return new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.d(TAG, "onClick: CodePro2 will handle the click event logic");
      }
    };
  }
}
