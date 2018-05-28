package me.onez.androiddagger2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.onez.androiddagger2.misc.DependencyA
import me.onez.androiddagger2.misc.DependencyB
import me.onez.androiddagger2.misc.DependencyC

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class DemoNoDagger2Activity : AppCompatActivity() {

  private lateinit var presenter: Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


    val dependencyA = DependencyA("A")
    val dependencyB = DependencyB("B")
    val dependencyC = DependencyC("C")

    /**
     * before you init presenter,you have to init dependencyA,dependencyB,dependencyC
     *
     * but for DemoNoDagger2Activity only need Presenter
     *
     * if DependencyA || DependencyB || DependencyC constructor changed..you need modify
     * DemoNoDagger2Activity
     */
    presenter = Presenter(dependencyA, dependencyB, dependencyC)
    presenter.doSomething()
  }


}