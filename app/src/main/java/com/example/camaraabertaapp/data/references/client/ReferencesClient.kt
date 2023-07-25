package com.example.camaraabertaapp.data.references.client

import com.example.camaraabertaapp.data.references.dto.PropositionThemesResponseDto
import retrofit2.Response
import retrofit2.http.GET

private const val PROPOSITION_REF_BASE_URL = "referencias/proposicoes"

interface ReferencesClient {

    @GET("$PROPOSITION_REF_BASE_URL/codTema")
    suspend fun getAllPropositionThemes(): Response<PropositionThemesResponseDto>

}
