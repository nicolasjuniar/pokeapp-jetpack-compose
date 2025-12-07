package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

sealed interface RegisterSignal {
    data class ShowToast(val message: String) : RegisterSignal
    object NavigateToMain : RegisterSignal
    object NavigateToLogin : RegisterSignal
}
