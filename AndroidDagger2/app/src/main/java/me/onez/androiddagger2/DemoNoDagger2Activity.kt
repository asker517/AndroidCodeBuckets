package me.onez.androiddagger2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
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
    setContentView(R.layout.activity_main)

    findViewById<Button>(R.id.btn_no_dagger2).setOnClickListener {
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

      Log.d("ZDT", presenter.toString())
      Log.d("ZDT",
          "d1 >" + presenter.d1?.toString() + " d2 > " + presenter.d2?.toString() + " d3 > " +
              presenter.d3?.toString())

    }

    findViewById<Button>(R.id.btn_simple_dagger2).setOnClickListener {
      startActivity(Intent(this@DemoNoDagger2Activity, SimpleDagger2Activity::class.java))
    }

    findViewById<Button>(R.id.btn_dep_dagger2).setOnClickListener {
      startActivity(Intent(this@DemoNoDagger2Activity, DepDagger2Activity::class.java))
    }

    findViewById<Button>(R.id.btn_sub_dagger2).setOnClickListener {
      startActivity(Intent(this@DemoNoDagger2Activity, SubDagger2Activity::class.java))
    }

  }


}