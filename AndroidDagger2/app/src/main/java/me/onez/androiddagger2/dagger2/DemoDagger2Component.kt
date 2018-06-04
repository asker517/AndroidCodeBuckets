package me.onez.androiddagger2.dagger2

import dagger.Component
import me.onez.androiddagger2.SimpleDagger2Activity

/**
 * <P>Created by Vincent on 2018/5/25.</P>
 */
@Component(modules = [DemoModule::class])
interface DemoDagger2Component {

  fun inject(activity: SimpleDagger2Activity)

}