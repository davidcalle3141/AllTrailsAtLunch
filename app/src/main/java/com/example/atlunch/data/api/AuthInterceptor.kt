package com.example.atlunch.data.api

import com.example.atlunch.MainApplication
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.dsl.koinApplication

class AuthInterceptor(private val key : String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("key",key).build()
        request  = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}