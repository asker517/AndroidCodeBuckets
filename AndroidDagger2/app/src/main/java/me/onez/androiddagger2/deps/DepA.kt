package me.onez.androiddagger2.deps

import android.util.Log

/**
 * <P>Created by Vincent on 2018/5/29.</P>
 */
class DepA(val name: String?) {


  fun showName() {
    Log.d("ZDT", "showName: $name")
  }

}