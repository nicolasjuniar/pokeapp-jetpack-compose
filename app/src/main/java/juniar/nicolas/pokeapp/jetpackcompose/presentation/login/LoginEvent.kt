package juniar.nicolas.pokeapp.jetpackcompose.presentation.login

sealed interface LoginEvent {
    data class UsernameChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object LoginButtonClicked : LoginEvent
    object RegisterTextClicked : LoginEvent
}

