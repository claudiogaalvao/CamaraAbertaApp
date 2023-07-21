package com.example.camaraabertaapp.datasource.references.dto

import com.google.gson.annotations.SerializedName

data class PropositionThemesResponseDto(
    @SerializedName(value = "dados")
    val data: List<ThemeDto>,
    val links: List<LinkDto>
)