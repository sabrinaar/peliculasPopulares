package com.sabrina.peliculaspopulares.vo

import com.google.gson.GsonBuilder
import com.sabrina.peliculaspopulares.domain.WebService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Sabrina Arrebillaga
 */
/*instancia de retrofit para trabajar con la url base*/
object RetrofitClient {

    const val API_KEY = "210c2545bb0679992dd2cb84412b0906"
    const val BASE_URL = "https://api.themoviedb.org/3/movie/"


    val webservice by lazy {


        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)   //retorna valor con @ annotation.
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)

    }
}