package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EstudiantesGrupo(
    @SerializedName("cedulaEstudiante") val cedulaEstudiante: String,
    @SerializedName("nombreEstudiante") var nombreEstudiante: String,
    @SerializedName("carreraEstudiante") var carreraEstudiante: String,
    @SerializedName("nombreCurso") var nombreCurso: String,
    @SerializedName("numeroGrupo") var numeroGrupo: Int,
    @SerializedName("nota") var nota: Float,
) : Serializable
