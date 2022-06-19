package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class Ciclo(
    @SerializedName("anno") val anno: Int,
    @SerializedName("numero") val numero: Int,
    @SerializedName("fechaInicio") var fechaInicio: String,
    @SerializedName("fechaFinal") var fechaFinal: String,
    @SerializedName("estado") var estado: String
) : Serializable {
    override fun toString(): String {
        return "${this.numero}-Ciclo ${this.anno}"
    }
}