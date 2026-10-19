package com.example.globetrotter.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val COUNTRY_BASE_URL = "https://api.restcountries.com/"
    private const val HOLIDAY_BASE_URL = "https://date.nager.at/"
    private const val SPORT_BASE_URL = "https://www.thesportsdb.com/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofitCountry: Retrofit = Retrofit.Builder()
        .baseUrl(COUNTRY_BASE_URL)
        .client(clienteHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitHoliday: Retrofit = Retrofit.Builder()
        .baseUrl(HOLIDAY_BASE_URL)
        .client(clienteHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitSport: Retrofit = Retrofit.Builder()
        .baseUrl(SPORT_BASE_URL)
        .client(clienteHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiServiceCountry: CountryApiService by lazy {
        retrofitCountry.create(CountryApiService::class.java)
    }

    val apiServiceHoliday: HolidayApiService by lazy {
        retrofitHoliday.create(HolidayApiService::class.java)
    }

    val apiServiceSport: SportApiService by lazy {
        retrofitSport.create(SportApiService::class.java)
    }
}
