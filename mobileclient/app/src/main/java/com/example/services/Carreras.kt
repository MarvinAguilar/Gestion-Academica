package com.example.services

import com.example.models.Carrera
import retrofit2.Call
import retrofit2.http.*

interface Carreras {
    @GET("carreras")
    fun getCarreras(): Call<List<Carrera>>

    @Headers("Content-Type: application/json")
    @POST("carrera")
    fun insertCarrera(@Body carrera: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("carrera")
    fun updateCarrera(@Body carrera: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "carrera", hasBody = true)
    fun deleteCarrera(@Body id: String): Call<Void>
}