package com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.claudiogalvaodev.camaraaberta.R
import com.claudiogalvaodev.camaraaberta.data.enums.BrazilStates
import com.claudiogalvaodev.camaraaberta.data.model.deputados.Deputado
import com.claudiogalvaodev.camaraaberta.ui.components.ColoredLabel
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import com.claudiogalvaodev.camaraaberta.utils.getThumbnailUrl
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime
import com.claudiogalvaodev.camaraaberta.utils.toReadableFullDate
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@Composable
fun PropositionDetailsScreen(
    propositionId: Int
) {
    val viewModel: PropositionDetailsViewModel = koinViewModel {
        parametersOf(propositionId)
    }
    val proposition by viewModel.proposition.collectAsState()

    CamaraAbertaTheme {
        proposition?.let { prop ->
            PropositionDetailsScreen(
                title = prop.getTitle(),
                status = prop.statusProposicao.descricaoSituacao,
                lastAppreciation = prop.statusProposicao.dataHora.toLocalDateTime().toLocalDate(),
                authors = listOf(Deputado.getMock("Deputado(a) Herculano Passos (MDB/SP)", BrazilStates.MG)),
                ementa = prop.ementa
            )
        }
    }
}

@Composable
private fun PropositionDetailsScreen(
    title: String,
    status: String,
    lastAppreciation: LocalDate,
    authors: List<Deputado>,
    ementa: String
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF22A87E))
                    .padding(top = 36.dp, bottom = 12.dp, start = 12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Última apreciação: ${lastAppreciation.toReadableFullDate()}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                    ColoredLabel(
                        text = status,
                        backgroundColor = Color.DarkGray
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(bottom = 24.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Autores",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        authors.forEach { author ->
                            val brazilStateFullName = remember {
                                BrazilStates.getBrazilStateByAcronym(author.ultimoStatus.siglaUf).fullName
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape),
                                    model = "https://www.camara.leg.br/internet/deputado/bandep/204554.jpg",
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = ColorPainter(Color.Gray)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Em ${author.ultimoStatus.situacao.lowercase()}",
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = author.nomeCivil,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Represente do Estado de $brazilStateFullName",
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Último relator",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val brazilStateFullName = remember {
                            BrazilStates.getBrazilStateByAcronym(authors[0].ultimoStatus.siglaUf).fullName
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape),
                                model = "https://www.camara.leg.br/internet/deputado/bandep/204554.jpg",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = ColorPainter(Color.Gray)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Em ${authors[0].ultimoStatus.situacao.lowercase()}",
                                    color = Color.DarkGray
                                )
                                Text(
                                    text = authors[0].nomeCivil,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Represente do Estado de $brazilStateFullName",
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Ementa",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ementa
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Seguir".uppercase(), color = Color(0xFF22A87E))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropositionDetailsScreenPreview() {
    PropositionDetailsScreen(
        title = "PL 7817/2017",
        status = "Aguardando Remessa à Sanção",
        lastAppreciation = LocalDate.of(2024, 4, 9),
        authors = listOf(Deputado.getMock("Deputado(a) Herculano Passos (MDB/SP)", BrazilStates.MG)),
        ementa = "Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e divulgação da demanda por vagas em creches nos Municípios e no Distrito Federal.\\r\\n\\r\\nNOVA EMENTA: Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e de divulgação da demanda por vagas no atendimento à educação infantil de crianças de 0 (zero) a 3 (três) anos de idade."
    )
}