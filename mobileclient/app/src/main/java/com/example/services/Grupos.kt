package com.example.services

import com.example.models.CursosCarrera
import com.example.models.Grupo
import com.example.models.GruposCarrera
import com.example.models.GruposProfesor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface Grupos {
    @Headers("Content-Type: application/json")
    @POST("grupos")
    fun getGrupos(@Body requestBody: String): Call<List<Grupo>>

    @Headers("Content-Type: application/json")
    @POST("grupos-profesor")
    fun getGruposProfesor(@Body requestBody: String): Call<List<GruposProfesor>>

    @Headers("Content-Type: application/json")
    @POST("grupos-carrera")
    fun getGruposCarrera(@Body requestBody: String): Call<List<GruposCarrera>>

    @Headers("Content-Type: application/json")
    @POST("grupo")
    fun insertGrupo(@Body grupo: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("grupos")
    fun updateGrupo(@Body grupo: String): Call<Void>
}