package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CursosCarrera(
    @SerializedName("codigoCurso") val codigoCurso: String,
    @SerializedName("nombreCurso") var nombreCurso: String,
) : Serializable {
    override fun toString(): String {
        return this.nombreCurso
    }
}
