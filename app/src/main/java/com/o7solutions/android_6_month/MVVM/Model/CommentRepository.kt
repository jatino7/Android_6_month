package com.o7solutions.android_6_month.MVVM.Model


class CommentRepository(private val apiService: ApiInterface) {
    suspend fun getComments() = apiService.getComments()
}