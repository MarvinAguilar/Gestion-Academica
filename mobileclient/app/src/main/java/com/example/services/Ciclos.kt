package com.example.services

import com.example.models.Carrera
import com.example.models.Ciclo
import retrofit2.Call
import retrofit2.http.*

interface Ciclos {
    @GET("ciclos")
    fun getCiclos(): Call<List<Ciclo>>

    @Headers("Content-Type: application/json")
    @POST("ciclos")
    fun insertCiclo(@Body ciclo: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("ciclos")
    fun updateCiclo(@Body ciclo: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "ciclos", hasBody = true)
    fun deleteCiclo(@Body id: String): Call<Void>
}