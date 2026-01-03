package juniar.nicolas.pokeapp.jetpackcompose.feature.login

sealed interface LoginEvent {
    data class UsernameChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object LoginButtonClicked : LoginEvent
    data object RegisterTextClicked : LoginEvent
}

