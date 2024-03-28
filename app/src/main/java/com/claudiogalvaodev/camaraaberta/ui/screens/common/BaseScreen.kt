package com.claudiogalvaodev.camaraaberta.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.claudiogalvaodev.camaraaberta.R
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme

@Composable
fun <T : Any> BaseScreen(
    state: BaseScreenState<T>,
    header: @Composable () -> Unit = {},
    content: @Composable (data: T) -> Unit
) {
    CamaraAbertaTheme {
        Surface {
            Column {
                header()
                when(state) {
                    is BaseScreenState.Loading -> {
                        // Loading
                        Loading()
                    }
                    is BaseScreenState.Empty -> {
                        // Empty
                        Empty()
                    }
                    is BaseScreenState.Error -> {
                        // Error
                        Text(text = "Error")
                    }
                    is BaseScreenState.Success -> {
                        content(state.data)
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp),
            color = Color(0xFF22A87E),
            trackColor = Color(0xFFE0E0E0)
        )
    }
}

@Composable
private fun Empty() {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.calendar)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            text = "Nenhum evento encontrado para data selecionada!",
            textAlign = TextAlign.Center
        )
        LottieAnimation(
            composition = lottieComposition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
private fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            text = "Algo deu errado!",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun LoadingPreview() {
    CamaraAbertaTheme {
        Loading()
    }
}

@Composable
@Preview
fun EmptyPreview() {
    CamaraAbertaTheme {
        Empty()
    }
}