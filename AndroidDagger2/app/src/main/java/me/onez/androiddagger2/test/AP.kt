package me.onez.androiddagger2.test

import android.util.Log
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/6/5.</P>
 */
class AP {

  private var tank: Tank

  @Inject constructor(tank: Tank) {
    this.tank = tank
    Log.d("ZDT", "tank is $tank")
  }
}