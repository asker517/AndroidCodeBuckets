package me.onez.androiddagger2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.onez.androiddagger2.dagger2.DaggerDemoDagger2Component
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class DemoDagger2Activity : AppCompatActivity() {

  @Inject
  lateinit var presenter: Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /**
     * after rebuild will produce the component class with prefix Dagger...
     * then call the component function to inject.
     */
    DaggerDemoDagger2Component.create().inject(this)
    presenter.doSomething()
  }

}
