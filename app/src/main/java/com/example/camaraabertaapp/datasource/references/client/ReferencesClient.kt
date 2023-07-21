package com.example.camaraabertaapp.datasource.references.client

import com.example.camaraabertaapp.datasource.references.dto.PropositionThemesResponseDto
import retrofit2.Response
import retrofit2.http.GET

private const val PROPOSITION_REF_BASE_URL = "referencias/proposicoes"

interface ReferencesClient {

    @GET("$PROPOSITION_REF_BASE_URL/codTema")
    suspend fun getAllPropositionThemes(): Response<PropositionThemesResponseDto>

}
