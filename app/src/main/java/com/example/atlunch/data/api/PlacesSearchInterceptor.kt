package com.example.atlunch.data.api

import com.example.atlunch.MainApplication
import com.example.atlunch.data.models.Restaurant
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.dsl.koinApplication
import java.lang.StringBuilder

class PlacesSearchInterceptor(private val key : String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("key",key)
            .addQueryParameter("type", Restaurant.TYPE)
            .addQueryParameter("fields", getRestaurantFieldsAsString())
            .build()
        request  = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    private fun getRestaurantFieldsAsString(): String {
        val sb = StringBuilder()
        Restaurant.DefaultFieldsList.forEach {
            sb.append(it)
            sb.append(',')
        }
        return sb.toString()
    }

}