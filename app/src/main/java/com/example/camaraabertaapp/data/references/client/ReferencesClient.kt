package com.example.camaraabertaapp.data.references.client

import com.example.camaraabertaapp.data.references.dto.PrepositionThemesResponseDto
import retrofit2.Response
import retrofit2.http.GET

private const val PREPOSITION_REF_BASE_URL = "referencias/proposicoes"

interface ReferencesClient {

    @GET("$PREPOSITION_REF_BASE_URL/codTema")
    suspend fun getAllPrepositionThemes(): Response<PrepositionThemesResponseDto>

}
