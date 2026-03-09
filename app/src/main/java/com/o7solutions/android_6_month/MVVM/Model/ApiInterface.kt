package com.o7solutions.android_6_month.MVVM.Model

import retrofit2.http.GET

interface ApiInterface {
    @GET("comments")
    suspend fun getComments(): List<Comment>
}