package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Grupo(
    @SerializedName("numero") val numero: Int,
    @SerializedName("horario") var horario: String,
    @SerializedName("cedulaProfesor") val cedulaProfesor: String,
    @SerializedName("nombreProfesor") val nombreProfesor: String,
    @SerializedName("codigoCurso") val codigoCurso: String,
    @SerializedName("nombreCurso") val nombreCurso: String,
    @SerializedName("annoCiclo") val annoCiclo: Int,
    @SerializedName("numeroCiclo") val numeroCiclo: Int
) : Serializable