package com.example.camaraabertaapp.data.references.dto

import com.example.camaraabertaapp.data.common.dto.LinkDto
import com.google.gson.annotations.SerializedName

data class PrepositionThemesResponseDto(
    @SerializedName(value = "dados")
    val data: List<ThemeDto>,
    val links: List<LinkDto>
)