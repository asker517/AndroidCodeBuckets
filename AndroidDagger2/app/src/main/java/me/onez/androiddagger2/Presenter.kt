package me.onez.androiddagger2

import android.util.Log
import me.onez.androiddagger2.dagger2.CDepend
import me.onez.androiddagger2.misc.DependencyA
import me.onez.androiddagger2.misc.DependencyB
import me.onez.androiddagger2.misc.DependencyC
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class Presenter(var d1: DependencyA?, var d2: DependencyB?, var d3: DependencyC?) {


//  @Inject
//  constructor() : this(null, null, null) {
//
//    Log.d("ZDT", "empty constructor called")
//
//  }

  /**
   * 多个构造器只能标注其中一个
   */

//    @Inject
//    constructor(d1: DependencyA) : this(d1, null, null) {
//
//      dependencyA = d1
//      Log.d("ZDT", dependencyA.toString())
//    }


//    @Inject
//    constructor(@Named("CC") d3: DependencyC) : this(null, null, d3) {
//      Log.d("ZDT", d3.toString())
//    }

  @Inject
  constructor(@CDepend d3: DependencyC) : this(null, null, d3) {
    Log.d("ZDT", d3.toString())
  }

  /**
   * 方法注入
   * 当Presenter构造器调用后马上会触发调用
   */

  //这种情况,等同于属性注入 NO PRIVATE
  //  @Inject
  //  lateinit var dependencyA: DependencyA

  private lateinit var dependencyA: DependencyA

  @Inject
  fun setDependencyA(dependencyA: DependencyA) {
    this.dependencyA = dependencyA
    Log.d("ZDT", "setDependencyA is called ")
    Log.d("ZDT", d1.toString())
  }


  //
  //  @Inject
  //  fun bindHolders(dependencyA: DependencyA){
  //    dependencyA.bindHolder(this)
  //  }

  fun doSomething() {
    Log.d("ZDT", "doSomething function called, dependencyA is :" + dependencyA?.toString())
  }

}
