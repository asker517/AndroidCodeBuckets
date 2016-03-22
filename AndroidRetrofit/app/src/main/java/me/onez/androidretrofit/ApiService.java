package me.onez.androidretrofit;

import me.onez.androidretrofit.model.CityInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Vincent on 15/12/16.
 */
public final class ApiService {

  public static final String BASE_URL = "http://apistore.baidu.com/";

  public interface City {
    //@Query: (http://apistore.baidu.com/microservice/cityinfo?cityname=xxx
    @GET("microservice/cityinfo")
    Call<CityInfo> getCityInfo(@Query("cityname") String cityName);

    @GET("microservice/cityinfo")
    Observable<CityInfo> getCityInfoRx(@Query("cityname") String cityName);
  }
}
