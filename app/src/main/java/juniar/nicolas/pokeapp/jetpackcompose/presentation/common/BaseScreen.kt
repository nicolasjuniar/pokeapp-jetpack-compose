package juniar.nicolas.pokeapp.jetpackcompose.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import juniar.nicolas.pokeapp.jetpackcompose.core.showToast
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.LoadingOverlay

@Composable
fun <T : BaseViewModel> BaseScreen(
    viewModel: T,
    content: @Composable () -> Unit
) {

    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.message.collect {
            context.showToast(it.orEmpty())
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isLoading) {
            LoadingOverlay()
        }
    }
}