package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.presentation.auth.AuthScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.detail.DetailScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.MainScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onLogin = { _, _ ->
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                },
                onRegister = { _, _ ->
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                })
        }

        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }

        composable(Screen.Detail.route) {
            DetailScreen(navController = navController)
        }
    }
}