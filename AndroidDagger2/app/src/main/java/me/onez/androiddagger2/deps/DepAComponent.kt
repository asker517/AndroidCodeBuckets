package me.onez.androiddagger2.deps

import dagger.Component

/**
 * <P>Created by Vincent on 2018/5/29.</P>
 */
@Component(modules = [DepAModule::class])
interface DepAComponent {
  fun getDepA(): DepA
}