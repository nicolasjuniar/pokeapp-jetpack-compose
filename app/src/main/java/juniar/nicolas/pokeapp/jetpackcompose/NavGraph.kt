package juniar.nicolas.pokeapp.jetpackcompose

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import juniar.nicolas.pokeapp.jetpackcompose.core.common.Screen
import juniar.nicolas.pokeapp.jetpackcompose.core.common.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.core.common.orEmpty
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.feature.camera.CameraScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.camera.PreviewScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.DashboardScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.PokemonDetailScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.login.LoginScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.registration.RegistrationScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.splash.SplashScreen

private const val MAIN_GRAPH = "main_graph"

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        route = MAIN_GRAPH
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                openMainScreen = {
                    navController.navigateScreen(
                        Screen.Dashboard.route,
                        Screen.Splash.route,
                        true
                    )
                },
                openLoginScreen = {
                    navController.navigateScreen(
                        Screen.Login.route,
                        Screen.Splash.route,
                        true
                    )
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                openRegisterScreen = {
                    navController.navigateScreen(
                        Screen.Registration.route,
                        Screen.Login.route,
                        true
                    )
                },
                openDashboardScreen = {
                    navController.navigateScreen(
                        Screen.Dashboard.route,
                        Screen.Login.route,
                        true
                    )
                }
            )
        }

        composable(Screen.Registration.route) {
            RegistrationScreen(
                openMainScreen = {
                    navController.navigateScreen(
                        Screen.Dashboard.route,
                        Screen.Splash.route,
                        true
                    )
                },
                openLoginScreen = {
                    navController.navigateScreen(
                        Screen.Login.route,
                        Screen.Splash.route,
                        true
                    )
                }
            )
        }

        composable(Screen.Dashboard.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MAIN_GRAPH)
            }
            val viewmodel: PreviewCameraViewmodel = viewModel(parentEntry)
            DashboardScreen(
                openLoginScreen = {
                    navController.navigateScreen(
                        Screen.Login.route,
                        Screen.Splash.route,
                        true
                    )
                },
                openDetailScreen = {
                    navController.navigateScreen(
                        Screen.Detail.createRoute(it)
                    )
                },
                openCameraScreen = {
                    navController.navigateScreen(Screen.Camera.route)
                },
                previewCameraViewModel = viewmodel
            )
        }

        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("pokedexNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokedexNumber = backStackEntry.arguments?.getInt("pokedexNumber")
            PokemonDetailScreen(
                pokedexNumber = pokedexNumber.orEmpty(1),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Camera.route) {
            CameraScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                openPreviewScreen = {
                    navController.navigateScreen(Screen.Preview.createRoute(it))
                }
            )
        }

        composable(
            Screen.Preview.route,
            arguments = listOf(navArgument("uri") { type = NavType.StringType })
        ) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri")
            val decodedUri = Uri.decode(uri)

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MAIN_GRAPH)
            }
            val viewmodel: PreviewCameraViewmodel = viewModel(parentEntry)
            PreviewScreen(
                imageUri = decodedUri,
                onBackClick = {
                    navController.popBackStack()
                },
                onCloseCamera = {
                    navController.popBackStack(
                        route = Screen.Camera.route,
                        inclusive = true
                    )
                },
                viewModel = viewmodel
            )
        }
    }
}
