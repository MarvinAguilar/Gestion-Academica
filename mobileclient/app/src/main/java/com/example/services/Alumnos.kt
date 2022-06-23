package com.example.services

import com.example.models.Alumno
import retrofit2.Call
import retrofit2.http.*

interface Alumnos {
    @GET("alumnos")
    fun getAlumnos(): Call<List<Alumno>>

    @Headers("Content-Type: application/json")
    @POST("alumno")
    fun getAlumno(@Body alumno: String): Call<Alumno>

    @Headers("Content-Type: application/json")
    @POST("alumnos")
    fun insertAlumno(@Body alumno: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("alumno")
    fun updateAlumno(@Body alumno: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "alumno", hasBody = true)
    fun deleteAlumno(@Body id: String): Call<Void>
}