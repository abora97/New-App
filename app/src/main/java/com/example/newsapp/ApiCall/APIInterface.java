package com.example.newsapp.ApiCall;


import com.example.newsapp.Constants;
import com.example.newsapp.model.headLines.ResponseHeadLines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    // https://newsapi.org/v2/top-headlines?country=us&apiKey=72a09f3a08aa416c8f394e4c75ef1435

    @GET("top-headlines")
    Call<ResponseHeadLines> getNews(@Query(Constants.COUNTRY) String country, @Query(Constants.API_KEY) String apiKey);


}
