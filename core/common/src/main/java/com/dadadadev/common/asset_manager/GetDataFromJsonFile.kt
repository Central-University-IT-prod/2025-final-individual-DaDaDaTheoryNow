package com.dadadadev.common.asset_manager

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
suspend inline fun <reified T> getDataFromJsonFile(
    context: Context,
    fileName: String,
): T =
    withContext(Dispatchers.IO) {
        AssetManager.open(context, fileName).use { inputStream ->
            Json.decodeFromStream(inputStream)
        }
    }