package me.onez.androiddagger2.test

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * <P>Created by Vincent on 2018/6/4.</P>
 */
@Module
class TestActivityModule{

  @Provides
  @Singleton
  fun provideTestDepA():TestDepA{
    return TestDepA()
  }

}