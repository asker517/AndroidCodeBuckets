package me.onez.androiddagger2.sub

import dagger.Module
import dagger.Provides
import me.onez.androiddagger2.App
import javax.inject.Singleton

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
@Module
class AppModule(app: App) {

  private var application = app

  @Singleton
  @Provides
  fun provideApplication(): App {
    return application
  }

//  @Singleton
//  @Provides
//  fun provideSharePreferences(context: App): SharedPreferences {
//    return PreferenceManager.getDefaultSharedPreferences(context)
//  }

}