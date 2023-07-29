package com.example.camaraabertaapp.data.events.dto

import com.google.gson.annotations.SerializedName

data class ChamberLocation(
    @SerializedName(value = "nome")
    val name: String,
    @SerializedName(value = "predio")
    val building: String?,
    @SerializedName(value = "andar")
    val floor: String?,
    @SerializedName(value = "sala")
    val room: String?
)