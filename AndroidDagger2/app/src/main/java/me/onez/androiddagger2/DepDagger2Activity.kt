package me.onez.androiddagger2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import me.onez.androiddagger2.deps.DaggerDepAComponent
import me.onez.androiddagger2.deps.DaggerDepBComponent
import me.onez.androiddagger2.deps.DaggerDepDagger2Component
import me.onez.androiddagger2.deps.DepB
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class DepDagger2Activity : AppCompatActivity() {

  @Inject
  lateinit var depB: DepB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_dagger2)
    findViewById<Button>(R.id.btn_show_log).visibility = View.GONE



    DaggerDepDagger2Component.builder()
        .depBComponent(DaggerDepBComponent.builder()
            .depAComponent(DaggerDepAComponent.create()).build())
        .build()
        .inject(this)


    Log.d("ZDT", depB.toString())
    depB?.testDepA()

  }

}
