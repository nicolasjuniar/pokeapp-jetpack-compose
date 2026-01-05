package juniar.nicolas.pokeapp.jetpackcompose.feature.splash

sealed interface SplashSignal {
    data object NavigateToMain : SplashSignal
    data object NavigateToLogin : SplashSignal
}
