package com.claudiogalvaodev.camaraaberta.data.model.deputados

import com.claudiogalvaodev.camaraaberta.data.enums.BrazilStates

data class Deputado(
    val cpf: String,
    val dataFalecimento: String?,
    val dataNascimento: String,
    val escolaridade: String,
    val id: Int,
    val municipioNascimento: String,
    val nomeCivil: String,
    val redeSocial: List<String>,
    val sexo: String,
    val ufNascimento: String,
    val ultimoStatus: UltimoStatus,
    val uri: String,
    val urlWebsite: String?
) {
    companion object {
        fun getMock(
            name: String,
            brazilState: BrazilStates
        ): Deputado {
            return Deputado(
                cpf = "000.000.000-00",
                dataFalecimento = null,
                dataNascimento = "01/01/2000",
                escolaridade = "Ensino Superior",
                id = 1,
                municipioNascimento = "São Paulo",
                nomeCivil = name,
                redeSocial = emptyList(),
                sexo = "M",
                ufNascimento = "MG",
                ultimoStatus = UltimoStatus(
                    condicaoEleitoral = "Titular",
                    data = "01/01/2020",
                    descricaoStatus = "Ativo",
                    email = "email",
                    gabinete = Gabinete(
                        andar = "1",
                        email = "",
                        nome = "Gabinete 1",
                        predio = "Anexo 1",
                        sala = "100",
                        telefone = "0000-0000"
                    ),
                    id = 1,
                    idLegislatura = 1,
                    nome = "Deputado",
                    nomeEleitoral = "Deputado",
                    siglaPartido = "ABC",
                    siglaUf = brazilState.name,
                    situacao = "Exercício",
                    uri = "uri",
                    uriPartido = "uriPartido",
                    urlFoto = "url",
                ),
                uri = "",
                urlWebsite = null
            )
        }
    }
}