package juniar.nicolas.pokeapp.jetpackcompose.feature.login

import androidx.compose.runtime.Composable

@Composable
fun LoginRoute(
    openRegisterScreen: () -> Unit,
    openDashboardScreen: () -> Unit
) {
    LoginScreen(
        openDashboardScreen = openDashboardScreen,
        openRegisterScreen = openRegisterScreen
    )
}