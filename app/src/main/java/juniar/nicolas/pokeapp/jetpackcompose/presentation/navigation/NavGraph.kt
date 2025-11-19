package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.presentation.auth.AuthScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.detail.DetailScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.MainScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Auth.route) {
            AuthScreen(navController = navController)
        }

        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }

        composable(Screen.Detail.route) {
            DetailScreen(navController = navController)
        }
    }
}