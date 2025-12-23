package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Dashboard : Screen("dashboard")
    data object List : Screen("list")
    data object Favorite : Screen("favorite")
    data object Profile : Screen("profile")
    data object Detail : Screen("detail/{pokedexNumber}") {
        fun createRoute(pokedexNumber: Int) = "detail/$pokedexNumber"
    }
}
