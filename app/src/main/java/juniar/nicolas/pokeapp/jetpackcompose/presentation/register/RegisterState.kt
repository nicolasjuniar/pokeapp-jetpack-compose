package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

data class RegisterState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)
