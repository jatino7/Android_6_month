package com.o7solutions.android_6_month.API

class DataRepository(val apiInterface: ApiInterface) {

    suspend fun getUsers(): List<UsersItem> = apiInterface.getUsers()
}