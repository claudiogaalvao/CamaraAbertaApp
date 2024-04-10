package com.claudiogalvaodev.camaraaberta.data.model.common

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("dados")
    val data: T,
    val links: List<Link>
)
