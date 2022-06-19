package com.example.services

import com.example.models.Alumno
import com.example.models.Usuario
import retrofit2.Call
import retrofit2.http.*

interface Usuarios {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body credentials: String): Call<String>

    @GET("usuarios")
    fun getUsuarios(): Call<List<Usuario>>

    @Headers("Content-Type: application/json")
    @POST("usuarios")
    fun insertUsuario(@Body usuario: String): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("usuarios")
    fun updateUsuario(@Body usuario: String): Call<Void>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "usuarios", hasBody = true)
    fun deleteUsuario(@Body id: String): Call<Void>
}