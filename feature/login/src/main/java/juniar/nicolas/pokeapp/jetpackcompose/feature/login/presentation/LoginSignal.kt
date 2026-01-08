package juniar.nicolas.pokeapp.jetpackcompose.feature.login.presentation

sealed interface LoginSignal {
    data class ShowToast(val message: String) : LoginSignal
    data object NavigateToMain : LoginSignal
    data object NavigateToRegister : LoginSignal
}
