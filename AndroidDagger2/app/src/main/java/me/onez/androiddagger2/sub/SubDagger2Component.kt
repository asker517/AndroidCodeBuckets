package me.onez.androiddagger2.sub

import dagger.Subcomponent
import me.onez.androiddagger2.SubDagger2Activity
import me.onez.androiddagger2.dagger2.PerActivity

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
@PerActivity
@Subcomponent
interface SubDagger2Component {
  fun inject(subDagger2Activity: SubDagger2Activity)
}