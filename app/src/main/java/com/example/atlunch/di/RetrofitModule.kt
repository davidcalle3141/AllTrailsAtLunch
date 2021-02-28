package com.example.atlunch.data.api

import com.example.atlunch.BuildConfig
import com.example.atlunch.R
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val placesRetrofitModule = module {

    factory { PlacesSearchInterceptor(androidApplication().getString(R.string.google_maps_key)) }
    factory { provideHttpClient(get()) }
    factory { providePlacesApi(get()) }
    single { providePlacesRetrofitClient(get()) }
}

fun providePlacesRetrofitClient(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder().baseUrl(BuildConfig.MAPS_API_URL).client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun provideHttpClient(interceptor: PlacesSearchInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
}

fun providePlacesApi(retrofit: Retrofit): PlacesApiService = retrofit.create(PlacesApiService::class.java)