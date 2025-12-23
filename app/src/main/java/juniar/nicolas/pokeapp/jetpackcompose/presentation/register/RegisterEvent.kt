package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

sealed interface RegisterEvent {
    data class UsernameChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class ConfirmPasswordChanged(val value: String) : RegisterEvent
    data object RegisterButtonClicked : RegisterEvent
    data object LoginTextClicked : RegisterEvent
}
