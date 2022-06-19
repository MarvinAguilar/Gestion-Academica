package com.example.services

import com.example.models.EstudiantesGrupo
import com.example.models.GruposEstudiante
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface EstudiantesGrupos {
    @Headers("Content-Type: application/json")
    @POST("estudiantes-grupo")
    fun getEstudiantesGrupo(@Body requestBody: String): Call<List<EstudiantesGrupo>>

    @Headers("Content-Type: application/json")
    @POST("grupos-estudiante")
    fun getHistorialEstudiante(@Body requestBody: String): Call<List<GruposEstudiante>>

    @Headers("Content-Type: application/json")
    @PUT("grupos-estudiante")
    fun ingresarNota(@Body requestBody: String): Call<Void>
}