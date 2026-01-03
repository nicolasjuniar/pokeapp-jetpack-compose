package juniar.nicolas.pokeapp.jetpackcompose.feature.login

import androidx.compose.runtime.Composable

@Composable
fun LoginRoute(
    openRegisterScreen: () -> Unit,
    openMainScreen: () -> Unit
) {
    LoginScreen(
        openMainScreen = openRegisterScreen,
        openRegisterScreen = openMainScreen
    )
}