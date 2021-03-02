package com.example.atlunch.data.api

import android.content.Context
import com.example.atlunch.BuildConfig
import com.example.atlunch.R
import okhttp3.HttpUrl

//TODO find better place for this maybe as an extension
fun getPlacesPhotoUrl(reference : String, context: Context): String{
    return HttpUrl.parse(BuildConfig.PHOTOS_API_URL)?.newBuilder()?.addQueryParameter("maxwidth", "250")
        ?.addQueryParameter("photoreference", reference)
        ?.addQueryParameter("key", context.getString(R.string.google_maps_key)).toString()
}