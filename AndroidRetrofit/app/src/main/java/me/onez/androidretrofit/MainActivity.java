package me.onez.androidretrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import me.onez.androidretrofit.model.CityInfo;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  private TextView show;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    show = (TextView) findViewById(R.id.tv_show);
    FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //sendRequest();
        sendRequestRx();
      }
    });
  }

  /**
   * 普通方式请求
   */
  private void sendRequest() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ApiService.City call = retrofit.create(ApiService.City.class);
    Call<CityInfo> info = call.getCityInfo("西安");
    info.enqueue(new Callback<CityInfo>() {
      @Override public void onResponse(Response<CityInfo> response, Retrofit retrofit) {
        show.setText(response.body().getRetData().cityName + " ------ " + response.body()
            .getRetData().cityCode);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  /**
   * Rx方式请求
   */
  private void sendRequestRx() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    ApiService.City rxCall = retrofit.create(ApiService.City.class);
    rxCall.getCityInfoRx("安康")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<CityInfo>() {
          @Override public void onCompleted() {
            Log.d(TAG, "onCompleted: ");
          }

          @Override public void onError(Throwable e) {
            Log.e(TAG, "onError: ");
          }

          @Override public void onNext(CityInfo cityInfo) {
            show.setText(
                cityInfo.getRetData().cityName + " ------ " + cityInfo.getRetData().cityCode);
          }
        });
  }
}
