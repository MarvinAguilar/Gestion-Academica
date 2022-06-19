package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Curso(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("creditos") var creditos: Int,
    @SerializedName("horasSemanales") var horasSemanales: Int,
    @SerializedName("codigoCarrera") val codigoCarrera: String,
    @SerializedName("nombreCarrera") var nombreCarrera: String
) : Serializable