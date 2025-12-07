package juniar.nicolas.pokeapp.jetpackcompose.presentation.login

sealed interface LoginSignal {
    data class ShowToast(val message: String) : LoginSignal
    object NavigateToMain : LoginSignal
    object NavigateToRegister : LoginSignal
}

