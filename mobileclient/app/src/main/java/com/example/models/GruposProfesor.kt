package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GruposProfesor(
    @SerializedName("numeroGrupo") val numeroGrupo: Int,
    @SerializedName("codigoCurso") var codigoCurso: String,
    @SerializedName("annoCiclo") val annoCiclo: Int,
    @SerializedName("numeroCiclo") val numeroCiclo: Int,
    @SerializedName("nombreCurso") val nombreCurso: String,
) : Serializable {
    override fun toString(): String {
        return "${this.numeroGrupo}- ${this.codigoCurso} ${this.nombreCurso}"
    }
}