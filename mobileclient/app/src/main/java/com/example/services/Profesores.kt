package com.example.services

import com.example.models.Curso
import com.example.models.Profesor
import retrofit2.Call
import retrofit2.http.*

interface Profesores {
    @GET("profesores")
    fun getProfesores(): Call<List<Profesor>>

    @Headers("Content-Type: application/json")
    @POST("profesor")
    fun insertProfesor(@Body profesor: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("profesor")
    fun updateProfesor(@Body profesor: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "profesor", hasBody = true)
    fun deleteProfesor(@Body id: String): Call<Void>
}