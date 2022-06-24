package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GruposCarrera(
    @SerializedName("numeroGrupo") val numeroGrupo: Int,
    @SerializedName("horario") var horario: String,
    @SerializedName("creditos") val creditos: Int,
    @SerializedName("horasSemanales") val horasSemanales: Int,
    @SerializedName("cedulaProfesor") val cedulaProfesor: String,
    @SerializedName("nombreProfesor") val nombreProfesor: String,
    @SerializedName("codigoCurso") val codigoCurso: String,
    @SerializedName("nombreCurso") val nombreCurso: String,
    @SerializedName("annoCiclo") val annoCiclo: Int,
    @SerializedName("numeroCiclo") val numeroCiclo: Int,
    @SerializedName("estadoMatricula") val estadoMatricula: String
) : Serializable
