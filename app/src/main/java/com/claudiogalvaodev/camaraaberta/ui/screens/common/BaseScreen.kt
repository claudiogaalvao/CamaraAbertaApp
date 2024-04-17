package com.claudiogalvaodev.camaraaberta.ui.screens.common

import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme

@Composable
fun <T : Any> BaseScreen(
    state: BaseScreenComponentState = rememberBaseScreenComponentState(),
    contentState: BaseScreenState<T>,
    header: @Composable () -> Unit = {},
    topButton: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.(data: T) -> Unit
) {
    CamaraAbertaTheme {
        Scaffold(
            topBar = {
                header()
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                when(contentState) {
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
                        content(contentState.data)
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.isTopButtonVisible,
                    enter = slideInVertically(),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    topButton()
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
fun rememberBaseScreenComponentState(
    key: Any? = null,
    isTopButtonVisible: Boolean = false
) = remember(key) {
    BaseScreenComponentState(
        isTopButtonVisible = isTopButtonVisible
    )
}

class BaseScreenComponentState(
    val isTopButtonVisible: Boolean = false,
)

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