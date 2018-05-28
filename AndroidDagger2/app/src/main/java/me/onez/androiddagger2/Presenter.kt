package me.onez.androiddagger2

import android.util.Log
import me.onez.androiddagger2.misc.DependencyA
import me.onez.androiddagger2.misc.DependencyB
import me.onez.androiddagger2.misc.DependencyC
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class Presenter(var d1: DependencyA?, var d2: DependencyB?, var d3: DependencyC?) {

  private lateinit var dependencyA: DependencyA

  @Inject
  constructor() : this(null, null, null) {

    Log.d("ZDT", "empty constructor called")

  }

  /**
   * 多个构造器只能标注其中一个
   */


  @Inject
  constructor(d1: DependencyA) : this(d1, null, null) {

    dependencyA = d1

    Log.d("ZDT", "DependencyA constructor called")

  }

  fun doSomething() {
    Log.d("ZDT", "doSomething function called")
  }


}