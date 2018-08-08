package me.onez.helloutils.kotlin

import android.text.TextUtils

class StrUtil private constructor() {

  init {
    throw UnsupportedOperationException("StrUtil cannot be instantiated")
  }

  companion object {

    /**
     * 判断一个字符串是否为null或空值.

     * @param str 指定的字符串
     * *
     * @return true or false
     */
    fun isEmpty(str: String): Boolean {
      return TextUtils.isEmpty(str) || str.trim { it <= ' ' }.length == 0
    }

    /**
     * 将null转化为“”.

     * @param str 指定的字符串
     * *
     * @return 字符串的String类型
     */
    fun parseEmpty(str: String?): String {
      var str = str
      if (str == null || "null" == str.trim { it <= ' ' }) {
        str = ""
      }
      return str.trim { it <= ' ' }
    }
  }
}