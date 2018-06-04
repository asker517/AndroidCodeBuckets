package me.onez.androiddagger2.sub

import dagger.Subcomponent
import me.onez.androiddagger2.dagger2.LocalScope

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
@LocalScope
@Subcomponent(modules = [PrefsModule::class])
interface PrefsComponent {

  //  fun getPrefs(): SharedPreferences
  fun plus(): SubDagger2Component

}