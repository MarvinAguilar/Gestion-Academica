package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GruposEstudiante(
    @SerializedName("numeroCiclo") val numeroCiclo: Int,
    @SerializedName("annoCiclo") var annoCiclo: Int,
    @SerializedName("codigoCurso") var codigoCurso: String,
    @SerializedName("nombreCurso") var nombreCurso: String,
    @SerializedName("creditosCurso") var creditosCurso: Int,
    @SerializedName("nota") var nota: Float,
) : Serializable