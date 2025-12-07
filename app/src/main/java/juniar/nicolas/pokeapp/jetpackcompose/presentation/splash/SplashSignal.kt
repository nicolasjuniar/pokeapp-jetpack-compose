package juniar.nicolas.pokeapp.jetpackcompose.presentation.splash

sealed interface SplashSignal {
    data object NavigateToMain : SplashSignal
    data object NavigateToLogin : SplashSignal
}
