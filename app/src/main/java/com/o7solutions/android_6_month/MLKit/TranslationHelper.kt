package com.o7solutions.android_6_month.MLKit

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await
import android.util.Log

class TranslationHelper {

    var translator: Translator ?= null


    suspend fun prepareTranslator() {

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()

        translator = Translation.getClient(options)

        var downloadOptions = DownloadConditions.Builder()
            .requireWifi().build()

        try {

            translator?.downloadModelIfNeeded(downloadOptions)?.await()
        } catch (e: Exception) {
            Log.e("Translation Helper",e.toString())
        }

    }


    suspend fun translateText(text: String): String {

        var ans = ""
         try {

            var ans = translator?.translate(text)?.await().toString() ?: ""

        } catch (e: Exception) {

             Log.e("Helper class",e.toString())

        }

        return ans

    }


    suspend fun close() {
        translator?.close()
    }
}