package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Carrera(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("titulo") var titulo: String
) : Serializable {
    override fun toString(): String {
        return this.nombre
    }
}