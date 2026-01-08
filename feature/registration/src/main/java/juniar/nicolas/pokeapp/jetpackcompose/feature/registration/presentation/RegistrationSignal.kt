package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.presentation

sealed interface RegistrationSignal {
    data class ShowToast(val message: String) : RegistrationSignal
    data object NavigateToMain : RegistrationSignal
    data object NavigateToLogin : RegistrationSignal
}
