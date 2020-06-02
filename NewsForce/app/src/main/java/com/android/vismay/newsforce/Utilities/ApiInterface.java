package com.android.vismay.newsforce.Utilities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<NewsModel> getTopicWiseNews(@Query("category") String domain,@Query("country") String country,@Query("language") String lang,@Query("apiKey") String key);

    @GET("top-headlines")
    Call<NewsModel> getTopicWiseNews2(@Query("country") String country,@Query("language") String lang,@Query("apiKey") String key);

    @GET("everything")
    Call<NewsModel> getTopicWiseNews3(@Query("q") String query,@Query("language") String lang,@Query("apiKey") String key);

    @GET("top-headlines")
    Call<NewsModel> getSourceWiseNews(@Query("sources") String sourceName,@Query("language") String lang,@Query("apiKey") String key);

    @GET("everything")
    Call<NewsModel> getSearchWiseNews(@Query("q") String q,@Query("language") String lang,@Query("apiKey") String key);
}
