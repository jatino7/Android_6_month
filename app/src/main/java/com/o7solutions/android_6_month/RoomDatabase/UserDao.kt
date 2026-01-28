package com.o7solutions.android_6_month.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {


    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    suspend fun getAllUSer(): List<User>



}