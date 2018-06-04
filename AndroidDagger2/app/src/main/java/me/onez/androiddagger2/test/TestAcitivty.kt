package me.onez.androiddagger2.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/6/4.</P>
 */
class TestActivity:AppCompatActivity(){

  @Inject
  lateinit var testDepA: TestDepA

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }



}