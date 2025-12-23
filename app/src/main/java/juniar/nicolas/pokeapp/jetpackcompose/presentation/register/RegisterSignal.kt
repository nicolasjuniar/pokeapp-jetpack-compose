package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

sealed interface RegisterSignal {
    data class ShowToast(val message: String) : RegisterSignal
    data object NavigateToMain : RegisterSignal
    data object NavigateToLogin : RegisterSignal
}
