package com.example.vincent.hellokotlin.why

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch

/**
 * <P>Created by Vincent on 2018/9/28.</P>
 */
class WhyCase {

  private fun getColumnIndex(column: String): String {

    //make request for column index & change it to string

    return "column index string result"
  }

  private fun getItems(columnIndex: String): List<Any> {

    //make request for items with column index parameters

    return arrayListOf()
  }


  private fun getData() {

    GlobalScope.launch(Dispatchers.Main) {

      val columnIndex = getColumnIndexKt("column")

      val items = getItemsKt(columnIndex)

      //then use items to do some thing....
      // update UI Here ??

    }
  }

  private fun requestColumnIndexAsync(index: (String) -> Unit) {
    index("")
  }

  private fun requestItemsAsync(columnIndex: String) {

  }

  private suspend fun getColumnIndexKt(column: String): String {

    return "column index string result"
  }

  private suspend fun getItemsKt(columnIndex: String): List<Any> {

    return arrayListOf()
  }

}
