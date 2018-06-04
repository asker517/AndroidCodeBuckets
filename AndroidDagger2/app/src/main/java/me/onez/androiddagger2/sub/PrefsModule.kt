package me.onez.androiddagger2.sub

import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import me.onez.androiddagger2.App
import me.onez.androiddagger2.dagger2.LocalScope

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
@Module
class PrefsModule {

  @LocalScope
  @Provides
  fun provideSharePreferences(app: App): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(app)
  }

}