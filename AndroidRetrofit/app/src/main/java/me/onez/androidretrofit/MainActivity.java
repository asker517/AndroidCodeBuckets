package me.onez.androidretrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import me.onez.androidretrofit.model.CityInfo;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  private TextView show;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    show = (TextView) findViewById(R.id.tv_show);
    FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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
    //info.execute() --> Sync
    //Async
    info.enqueue(new Callback<CityInfo>() {
      @Override
      public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
        show.setText(response.body().getRetData().cityName + " ------ " + response.body()
            .getRetData().cityCode);
      }

      @Override
      public void onFailure(Call<CityInfo> call, Throwable t) {

      }
    });
  }

  /**
   * 单独配置OkHttpClient
   */
  private OkHttpClient getOkClient() {
    return new OkHttpClient.Builder().retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();
  }

  /**
   * Rx方式请求
   */
  private void sendRequestRx() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(getOkClient())
        .build();

    ApiService.City rxCall = retrofit.create(ApiService.City.class);
    rxCall.getCityInfoRx("安康")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<CityInfo>() {
          @Override
          public void onCompleted() {
            Log.d(TAG, "onCompleted: ");
          }

          @Override
          public void onError(Throwable e) {
            Log.e(TAG, "onError: ");
          }

          @Override
          public void onNext(CityInfo cityInfo) {
            show.setText(
                cityInfo.getRetData().cityName + " ------ " + cityInfo.getRetData().cityCode);
          }
        });
  }
}
