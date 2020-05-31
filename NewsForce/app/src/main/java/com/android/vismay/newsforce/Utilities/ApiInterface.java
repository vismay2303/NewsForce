package com.android.vismay.newsforce.Utilities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("everything")
    Call<NewsModel> getSportsNews(@Query("q") String domain,@Query("language") String lang,@Query("apiKey") String key);


}
