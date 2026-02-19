package com.o7solutions.android_6_month.Appwrite

import android.content.Context
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite._File
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import java.io.File

class AppwriteManager private  constructor(context: Context){


    private val client = Client(context)
        .setEndpoint("https://fra.cloud.appwrite.io/v1")
        .setProject("68402a5a000611723049")


    private val storage = Storage(client)


    companion object {

        @Volatile

        private var INSTANCE: AppwriteManager ?= null

        fun getInstance(context: Context): AppwriteManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppwriteManager(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    suspend fun uploadImage(bucketId: String,file: File): io.appwrite.models.File {


        return storage.createFile(
            bucketId = bucketId,
            fileId =  ID.unique(),
            file = InputFile.fromFile(file)
        )

    }

}