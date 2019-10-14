package com.example.mvppatterntest.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("/movie.json")
    fun test(): Call<String>

}