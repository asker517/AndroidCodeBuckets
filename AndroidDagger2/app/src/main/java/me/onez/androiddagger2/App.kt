package me.onez.androiddagger2

import android.app.Application
import me.onez.androiddagger2.sub.AppComponent
import me.onez.androiddagger2.sub.AppModule
import me.onez.androiddagger2.sub.DaggerAppComponent

/**
 * <P>Created by Vincent on 2018/5/30.</P>
 */
class App : Application() {

  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()

    appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
  }
}