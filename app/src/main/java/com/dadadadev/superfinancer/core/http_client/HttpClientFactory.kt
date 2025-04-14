package com.dadadadev.superfinancer.core.http_client

import android.app.Application
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File

object HttpClientFactory {
    fun create(context: Application?): HttpClient {
        return HttpClient {
            if (context != null) {
                install(HttpCache) {
                    val cacheDir = File(context.cacheDir, "http_cache")
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs()
                    }
                    publicStorage(FileStorage(cacheDir))
                }
            }

            install(DefaultRequest) {
                headers.append("Cache-Control", "max-age=3600")
            }

            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(HttpTimeout)
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}