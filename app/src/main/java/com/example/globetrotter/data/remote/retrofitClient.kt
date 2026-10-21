package com.example.globetrotter.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"
    private const val CAT_FACT_BASE_URL = "https://catfact.ninja/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofitWeather: Retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .client(clienteHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitCatFact: Retrofit = Retrofit.Builder()
        .baseUrl(CAT_FACT_BASE_URL)
        .client(clienteHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiServiceWeather: WeatherApiService by lazy {
        retrofitWeather.create(WeatherApiService::class.java)
    }

    val apiServiceCatFact: CatFactApiService by lazy {
        retrofitCatFact.create(CatFactApiService::class.java)
    }
}
