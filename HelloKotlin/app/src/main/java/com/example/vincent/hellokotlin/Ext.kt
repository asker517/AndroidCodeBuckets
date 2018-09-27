package com.example.vincent.hellokotlin

import android.view.View
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.channels.actor

/**
 * <P>Created by Vincent on 2018/9/18.</P>
 */
fun View.onClick(action: suspend (View) -> Unit) {
  val eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
    for (event in channel) action(event)
  }
  setOnClickListener { eventActor.offer(it) }
}