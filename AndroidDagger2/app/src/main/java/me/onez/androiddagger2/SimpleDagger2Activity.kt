package me.onez.androiddagger2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import me.onez.androiddagger2.dagger2.DaggerDemoDagger2Component
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class SimpleDagger2Activity : AppCompatActivity() {

  @Inject
  lateinit var presenter: Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_dagger2)

    findViewById<Button>(R.id.btn_show_log).visibility = View.GONE
    /**
     * after rebuild will produce the component class with prefix Dagger...
     * then call the component function to inject.
     */
    DaggerDemoDagger2Component.create().inject(this)
    presenter.doSomething()

    Log.d("ZDT", presenter.toString())
    Log.d("ZDT",
        "d1 > " + presenter.d1?.toString() + " d2 > " + presenter.d2?.toString() + " d3 > " +
            presenter.d3?.toString())
  }

}
