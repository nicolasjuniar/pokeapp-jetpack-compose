package juniar.nicolas.pokeapp.jetpackcompose.feature.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    openMainScreen: () -> Unit = {},
    openLoginScreen: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is SplashSignal.NavigateToMain -> openMainScreen()
                is SplashSignal.NavigateToLogin -> openLoginScreen()
            }
        }
    }

    SplashContent()
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen()
    }
}
