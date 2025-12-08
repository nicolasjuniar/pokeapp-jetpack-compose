package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import juniar.nicolas.pokeapp.jetpackcompose.core.orEmpty
import juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.DashboardScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.detail.PokemonDetailScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.login.LoginScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.register.RegisterScreen
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

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("pokedexNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokedexNumber = backStackEntry.arguments?.getInt("pokedexNumber")
            PokemonDetailScreen(
                pokedexNumber = pokedexNumber.orEmpty(1),
                navController = navController
            )
        }
    }
}
