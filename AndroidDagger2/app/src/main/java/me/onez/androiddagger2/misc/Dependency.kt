package me.onez.androiddagger2.misc

import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */

data class DependencyA @Inject constructor(var name: String)


data class DependencyB(var name: String)


data class DependencyC(var name: String)
