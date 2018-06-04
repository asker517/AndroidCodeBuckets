package me.onez.androiddagger2

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import me.onez.androiddagger2.sub.PrefsModule
import javax.inject.Inject

/**
 * <P>Created by Vincent on 2018/5/24.</P>
 */
class SubDagger2Activity : AppCompatActivity() {

  @Inject
  lateinit var prefs: SharedPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_dagger2)
    findViewById<Button>(R.id.btn_show_log).visibility = View.GONE


    //    DaggerSubDagger2Component.builder()
    //        .appComponent(DaggerAppComponent.builder()
    //            .appModule(AppModule(application as App))
    //            .build())
    //        .build()
    //        .inject(this)

    (application as App).appComponent.plus(PrefsModule()).plus().inject(this)

    Log.d("ZDT", "onCreate: $prefs")

  }

}
