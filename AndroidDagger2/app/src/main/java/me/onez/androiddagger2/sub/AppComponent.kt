package me.onez.androiddagger2.sub

import dagger.Component
import javax.inject.Singleton

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun plus(prefsModule: PrefsModule): PrefsComponent

}