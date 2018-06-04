package me.onez.androiddagger2.dagger2

import dagger.Module
import dagger.Provides
import me.onez.androiddagger2.misc.DependencyA
import me.onez.androiddagger2.misc.DependencyB
import me.onez.androiddagger2.misc.DependencyC
import javax.inject.Named

/**
 * <P>Created by Vincent on 2018/5/28.</P>
 */
@Module
class DemoModule {

  @Provides
  fun provideDependencyA(): DependencyA {
    return DependencyA("AAA")
  }

  @Provides
  fun provideDependencyB(): DependencyB {
    return DependencyB("BBB")
  }



  @Provides
  @Named("C")
  fun provideDependencyC(): DependencyC {
    return DependencyC("C")
  }

  @Provides
  @Named("CC")
  fun provideDependencyCC(): DependencyC {
    return DependencyC("CC")
  }

  @Provides
  @CDepend
  fun provideDependencyCCC(): DependencyC {
    return DependencyC("CCC")
  }

}