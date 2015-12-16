package me.onez.databinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import me.onez.databinding.BR;

/**
 * Created by Vincent on 15/12/10.
 * Observable Binding方式1
 */
public class CoderPro1 extends BaseObservable {
  private String name;
  private String gender;
  private String info;
  private String actionName;

  @Bindable
  public String getName() {
    return name;
  }

  @Bindable
  public String getGender() {
    return gender;
  }

  @Bindable
  public String getInfo() {
    return info;
  }


  public void setName(String name) {
    this.name = name;
    notifyPropertyChanged(BR.name);
  }

  public void setGender(String gender) {
    this.gender = gender;
    notifyPropertyChanged(BR.gender);
  }

  public void setInfo(String info) {
    this.info = info;
    notifyPropertyChanged(BR.info);
  }

  public String getActionName() {
    return actionName;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }
}
