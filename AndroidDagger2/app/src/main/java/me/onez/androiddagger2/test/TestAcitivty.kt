package me.onez.androiddagger2.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/6/4.</P>
 */
class TestActivity : AppCompatActivity() {

  @Inject
  lateinit var testDepA: TestDepA

  @Inject
  lateinit var ap: AP

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerTestActivityComponent.create().inject(this)
    Log.d("ZDT", "onCreate: $ap")

  }


}