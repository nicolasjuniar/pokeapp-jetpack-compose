package juniar.nicolas.pokeapp.jetpackcompose.feature.registration

data class RegistrationState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)
