package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    openMainScreen: () -> Unit = {},
    openLoginScreen: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is RegistrationSignal.ShowToast -> context.showToast(it.message)
                is RegistrationSignal.NavigateToMain -> openMainScreen()
                is RegistrationSignal.NavigateToLogin -> openLoginScreen()
            }
        }
    }
    RegistrationContent(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent
    )
}
