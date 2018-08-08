package me.onez.helloutils.kotlin

import android.content.Context
import android.util.TypedValue

/**
 * <P>Created by Vincent on 2017/9/2.</P>
 */
class DensityUtil private constructor() {

  init {
    throw UnsupportedOperationException("DensityUtil cannot be instantiate")
  }

  companion object {

    fun dip2px(context: Context, dipValue: Int): Int {
      val scal = context.resources.displayMetrics.density
      return (dipValue * scal + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
      val scale = context.resources.displayMetrics.density
      return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(context: Context, pxValue: Float): Float {
      return pxValue / context.resources.displayMetrics.scaledDensity
    }

    fun sp2px(context: Context, spValue: Int): Int {
      return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue.toFloat(), context.resources
          .displayMetrics)).toInt()
    }

  }

}