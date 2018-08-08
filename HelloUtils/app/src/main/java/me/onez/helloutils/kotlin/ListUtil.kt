package me.onez.helloutils.kotlin

/**
 * <P>Created by Vincent on 2017/9/2.</P>
 */
class ListUtil private constructor() {

  init {
    throw UnsupportedOperationException("ListUtil cannot be instantiate")
  }

  companion object {

    fun <V> getSize(sourceList: List<V>?): Int {
      return sourceList?.size ?: 0
    }

    fun <V> isEmpty(sourceList: List<V>?): Boolean {
      return sourceList == null || sourceList.size == 0
    }

  }

}