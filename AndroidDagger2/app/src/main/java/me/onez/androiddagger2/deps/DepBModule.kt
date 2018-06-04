package me.onez.androiddagger2.deps

import dagger.Module
import dagger.Provides

/**
 * <P>Created by Vincent on 2018/5/29.</P>
 */
@Module
class DepBModule {

  @Provides
  fun provideDepB(depA: DepA): DepB {
    return DepB(depA)
  }


}