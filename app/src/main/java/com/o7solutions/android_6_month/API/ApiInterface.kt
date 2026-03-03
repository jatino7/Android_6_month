package com.o7solutions.android_6_month.API

import retrofit2.http.GET

interface ApiInterface {

    @GET("users")
    suspend fun getUsers(): List<UsersItem>
}