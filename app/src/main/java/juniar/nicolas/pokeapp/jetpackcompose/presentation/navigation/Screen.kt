package juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main")
    object List : Screen("list")
    object Favorite : Screen("favorite")
    object Detail : Screen("detail")
}