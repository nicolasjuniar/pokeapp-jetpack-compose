package juniar.nicolas.pokeapp.jetpackcompose.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import juniar.nicolas.pokeapp.jetpackcompose.core.showToast
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.LoadingOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : BaseViewModel> BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: T,
    navController: NavController? = null,
    title: String? = null,
    content: @Composable () -> Unit
) {

    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.message.collect {
            context.showToast(it.orEmpty())
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (title != null) {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        if (navController != null) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                content()

                if (isLoading) {
                    LoadingOverlay()
                }
            }
        })
}