package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.common.Screen
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.model.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    openLoginScreen: () -> Unit = {},
    openDetailScreen: (pokedexNumber: Int) -> Unit = {},
    openCameraScreen: () -> Unit = {},
    previewCameraViewModel: PreviewCameraViewmodel
) {
    val bottomNavController = rememberNavController()
    val navItems = listOf(
        NavItem(
            route = Screen.List.route,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            label = "List"
        ), NavItem(
            route = Screen.Favorite.route,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            label = "Favorite"
        ), NavItem(
            route = Screen.Profile.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            label = "Profile"
        )
    )

    val state by viewModel.state.collectAsStateWithLifecycle()


    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            if (it is DashboardSignal.NavigateToLogin) {
                context.showToast("Logout Successful")
                openLoginScreen()
            }
        }
    }

    DashboardContent(
        state = state,
        onEvent = viewModel::onEvent,
        navItems = navItems,
        bottomNavController = bottomNavController,
        openDetailScreen = openDetailScreen,
        openCameraScreen = openCameraScreen,
        previewCameraViewModel = previewCameraViewModel
    )
}
