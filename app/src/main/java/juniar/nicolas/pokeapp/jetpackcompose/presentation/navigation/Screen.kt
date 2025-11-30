package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object List : Screen("list")
    object Favorite : Screen("favorite")
    object Detail : Screen("detail/{pokedexNumber}") {
        fun createRoute(pokedexNumber: Int) = "detail/$pokedexNumber"
    }
}