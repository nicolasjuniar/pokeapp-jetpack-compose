package juniar.nicolas.pokeapp.jetpackcompose.feature.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)
