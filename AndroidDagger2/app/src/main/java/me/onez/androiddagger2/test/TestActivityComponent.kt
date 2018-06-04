package me.onez.androiddagger2.test

import dagger.Component
import javax.inject.Singleton

/**
 * <P>Created by Vincent on 2018/6/4.</P>
 */
@Singleton
@Component(modules = [TestActivityModule::class])
interface TestActivityComponent {

  fun inject(testActivity: TestActivity)

}