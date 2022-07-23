package com.sarahisweird.uwebot.services.pokeapi.data.metadata.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@JsonClass(generateAdapter = true)
data class NamedAPIResource<T : Any>(
    val name: String,
    val url: String,
) {
    fun load(client: OkHttpClient, moshi: Moshi, clazz: Class<T>): T? {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Failed to load resource $url")

            return moshi.adapter(clazz).fromJson(response.body!!.source())
        }
    }
}
