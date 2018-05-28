package me.onez.androiddagger2.misc

import me.onez.androiddagger2.Presenter
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */

data class DependencyA @Inject constructor(var name: String){

  fun bindHolder(presenter: Presenter){
    TODO()
  }
}


data class DependencyB(var name: String)


data class DependencyC(var name: String)
