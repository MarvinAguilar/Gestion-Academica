package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Alumno(
    @SerializedName("cedula") val cedula: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("telefono") var telefono: String,
    @SerializedName("email") var email: String,
    @SerializedName("fechaNacimiento") var fechaNacimiento: String,
    @SerializedName("codigoCarrera") val codigoCarrera: String,
    @SerializedName("nombreCarrera") val nombreCarrera: String
) : Serializable