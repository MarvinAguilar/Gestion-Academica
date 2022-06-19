package com.example.services

import com.example.models.Curso
import com.example.models.CursosCarrera
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface Cursos {
    @GET("cursos")
    fun getCursos(): Call<List<Curso>>

    @Headers("Content-Type: application/json")
    @POST("curso")
    fun insertCurso(@Body curso: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("curso")
    fun updateCurso(@Body curso: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "curso", hasBody = true)
    fun deleteCurso(@Body id: String): Call<Void>
}