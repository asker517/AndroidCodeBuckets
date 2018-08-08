package me.onez.helloutils.kotlin

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * <P>Created by Vincent on 2017/8/30.</P>
 */
class AppUtil private constructor() {

  init {
    throw UnsupportedOperationException("AppUtil cannot be instantiate")
  }

  companion object {

    /**
     * 获取包信息
     */
    fun getPackageInfo(context: Context): PackageInfo? {
      var info: PackageInfo? = null
      try {
        val packageName = context.packageName
        info = context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
      } catch(e: Exception) {
        e.printStackTrace()
      }
      return info
    }
  }

  /**
   * 获取App版本名
   */
  fun getAppVersionName(context: Context): String {
    val pm = context.packageManager
    val pi: PackageInfo
    try {
      pi = pm.getPackageInfo(context.packageName, 0)
      return pi.versionName
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return ""
  }

  /**
   * 获取App版本号
   */
  fun getAppVersionCode(context: Context): Int {
    val pm = context.packageManager
    var pi: PackageInfo
    try {
      pi = pm.getPackageInfo(context.packageName, 0)
      return pi.versionCode
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return 0
  }
}