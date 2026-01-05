package juniar.nicolas.pokeapp.jetpackcompose.feature.registration

sealed interface RegistrationEvent {
    data class UsernameChanged(val value: String) : RegistrationEvent
    data class PasswordChanged(val value: String) : RegistrationEvent
    data class ConfirmPasswordChanged(val value: String) : RegistrationEvent
    data object RegisterButtonClicked : RegistrationEvent
    data object LoginTextClicked : RegistrationEvent
}
