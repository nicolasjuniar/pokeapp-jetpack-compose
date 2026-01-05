package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.common.Screen
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.SimpleDialog
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.model.NavItem
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.favorite.FavoriteScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.list.ListScreen
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.ProfileScreen

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

    var showLogoutDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is DashboardSignal.NavigateToLogin -> {
                    context.showToast("Logout Successful")
                    openLoginScreen()
                }

                is DashboardSignal.ShowLogoutDialog -> {
                    showLogoutDialog = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hello ${state.username}") }, actions = {
                TextButton(onClick = { showLogoutDialog = true }) {
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

    if (showLogoutDialog) {
        SimpleDialog("Confirm Logout", "Are you sure you want to logout", showOnChange = {
            showLogoutDialog = it
        }, confirmOnClick = {
            viewModel.onEvent(DashboardEvent.ConfirmLogoutClick)
        })
    }
}
