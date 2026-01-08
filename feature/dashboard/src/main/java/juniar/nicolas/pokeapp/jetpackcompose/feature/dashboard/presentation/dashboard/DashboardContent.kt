package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.common.Screen
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.SimpleDialog
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.model.NavItem
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.favorite.FavoriteScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.list.ListScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardState = DashboardState(),
    onEvent: (DashboardEvent) -> Unit = {},
    navItems: List<NavItem> = listOf(),
    bottomNavController: NavHostController = rememberNavController(),
    openDetailScreen: (pokedexNumber: Int) -> Unit = {},
    openCameraScreen: () -> Unit = {},
    previewCameraViewModel: PreviewCameraViewmodel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hello ${state.username}") }, actions = {
                TextButton(onClick = { onEvent(DashboardEvent.LogoutTextClick) }) {
                    Text("Logout")
                }
            })
        },

        bottomBar = {
            NavigationBar {
                val currentDestination =
                    bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                navItems.forEach { item ->
                    NavigationBarItem(selected = currentDestination == item.route, onClick = {
                        bottomNavController.navigate(item.route) {
                            popUpTo(bottomNavController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }, icon = {
                        Icon(
                            imageVector = if (currentDestination == item.route) item.selectedIcon
                            else item.unselectedIcon, contentDescription = item.label
                        )
                    }, label = { Text(item.label) })
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.List.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.List.route) {
                ListScreen(
                    modifier = Modifier.fillMaxSize(), openDetailScreen = {
                        openDetailScreen(it)
                    })
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    modifier = Modifier.fillMaxSize(), openDetailScreen = {
                        openDetailScreen(it)
                    })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    openCameraScreen = {
                        openCameraScreen()
                    },
                    previewCameraViewmodel = previewCameraViewModel
                )
            }
        }
    }

    if (state.showLogoutDialog) {
        SimpleDialog(
            "Confirm Logout",
            "Are you sure you want to logout",
            onDismiss = {
                onEvent(DashboardEvent.OnDismissLogoutDialog)
            },
            confirmOnClick = {
                onEvent(DashboardEvent.ConfirmLogoutClick)
            }
        )
    }
}

@Preview
@Composable
fun DashboardContentPreview() {
    DashboardContent()
}