package me.onez.androiddagger2.deps

import dagger.Component
import me.onez.androiddagger2.DepDagger2Activity

/**
 * <P>Created by Vincent on 2018/5/29.</P>
 */
@Component(dependencies = [DepBComponent::class])
interface DepDagger2Component {
  fun inject(depDagger2Activity: DepDagger2Activity)
}