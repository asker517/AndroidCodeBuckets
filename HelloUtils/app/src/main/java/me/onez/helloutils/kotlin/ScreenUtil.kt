package me.onez.helloutils.kotlin

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenUtil private constructor() {

  init {
    throw UnsupportedOperationException("ScreenUtil cannot be instantiated")
  }

  companion object {

    private var screenWidth = 0

    private var screenHeight = 0

    fun getScreenWidth(context: Context): Int {
      if (screenWidth == 0) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        screenWidth = outMetrics.widthPixels
      }
      return screenWidth
    }

    fun getScreenHeight(context: Context): Int {
      if (screenHeight == 0) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        screenHeight = outMetrics.heightPixels
      }
      return screenHeight
    }

  }
}