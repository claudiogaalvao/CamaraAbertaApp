package com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.claudiogalvaodev.camaraaberta.ui.components.ColoredLabel
import com.claudiogalvaodev.camaraaberta.utils.getThumbnailUrl

@Composable
fun PropositionDetailsScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                        text = "Projeto de Lei",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    ColoredLabel(
                        text = "Aguardando Remessa à Sanção",
                        backgroundColor = Color.DarkGray
                    )
                }
//            Image(
//                painter = painterResource(id = R.drawable.plenario_camara_dos_deputados_cheio),
//                contentDescription = null
//            )
            }
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Urgência (Art. 155, RICD)",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Última apreciação: 09 abril 2024",
                    color = Color.Black,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Autores",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
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
                    Text(
                        text = "Deputado(a) Herculano Passos (MDB/SP)",
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Ementa",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e divulgação da demanda por vagas em creches nos Municípios e no Distrito Federal.\\r\\n\\r\\nNOVA EMENTA: Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e de divulgação da demanda por vagas no atendimento à educação infantil de crianças de 0 (zero) a 3 (três) anos de idade."
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22A87E),
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "A favor")
            }
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Contra")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PropositionDetailsScreenPreview() {
    PropositionDetailsScreen()
}