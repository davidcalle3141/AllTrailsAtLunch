package com.example.atlunch.data.api

import okhttp3.Interceptor
import okhttp3.Response

class PlacesSearchInterceptor(private val key : String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("key",key)
            .addQueryParameter("type", TYPE)
            .addQueryParameter("rankby", RANKBY)
            .build()
        request  = request.newBuilder().url(url).build()
        return chain.proceed(request)

    }
    companion object{
        const val TYPE = "restaurant"
        const val RANKBY = "distance"
    }

}