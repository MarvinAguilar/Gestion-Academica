package com.example.services

import com.example.models.CursosCarrera
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CursosCarrera {
    @Headers("Content-Type: application/json")
    @POST("curso-carrera-ciclo")
    fun getCursosCarrera(@Body curso: String): Call<List<CursosCarrera>>
}