package com.example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("cedula") val cedula: String,
    @SerializedName("clave") var clave: String,
    @SerializedName("perfil") var perfil: Int
) : Serializable