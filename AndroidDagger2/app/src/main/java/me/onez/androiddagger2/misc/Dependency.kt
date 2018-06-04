package me.onez.androiddagger2.misc

import android.util.Log
import me.onez.androiddagger2.Presenter

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */

data class DependencyA(var name: String) {

  fun bindHolder(presenter: Presenter) {
    Log.d("ZDT", "dependencyA bindHolder called")
  }
}


data class DependencyB(var name: String)


data class DependencyC(var name: String)
