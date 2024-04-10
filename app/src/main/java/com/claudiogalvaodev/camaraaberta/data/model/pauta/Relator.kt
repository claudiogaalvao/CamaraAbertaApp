package com.claudiogalvaodev.camaraaberta.data.model.pauta

data class Relator(
    val email: String,
    val id: Int,
    val idLegislatura: Int,
    val nome: String,
    val siglaPartido: String,
    val siglaUf: String,
    val uri: String,
    val uriPartido: String,
    val urlFoto: String
) {
    companion object {
        fun getMock() = Relator(
            email = "email",
            id = 1,
            idLegislatura = 1,
            nome = "nome",
            siglaPartido = "siglaPartido",
            siglaUf = "siglaUf",
            uri = "uri",
            uriPartido = "uriPartido",
            urlFoto = "urlFoto"
        )
    }
}