package com.example.hopskipdrivecodingchallenge.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val JSON_URL = "https://storage.googleapis.com/hsd-interview-resources/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateTimeAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(JSON_URL)
    .build()

class DateTimeAdapter {
    @ToJson
    fun toJson(dateTime: LocalDateTime): String = dateTime.toString()

    @FromJson
    fun fromJson(value: String): LocalDateTime =
        LocalDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME)
}

interface RidesApiService {
    @GET("simplified_my_rides_response.json")
    suspend fun getRides(): Rides
}

object RidesApi {
    val ridesApiService: RidesApiService by lazy {
        retrofit.create(RidesApiService::class.java)
    }
}