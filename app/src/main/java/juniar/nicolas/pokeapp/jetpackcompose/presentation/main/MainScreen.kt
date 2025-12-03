package juniar.nicolas.pokeapp.jetpackcompose.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.core.showToast
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.SimpleDialog
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.favorite.FavoriteScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list.ListScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController, viewModel: MainViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()
    val navItems = listOf(
        Screen.List.route to Icons.AutoMirrored.Filled.List,
        Screen.Favorite.route to Icons.Filled.Favorite
    )

    val username by viewModel.username.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.logoutEvent.collect {
            context.showToast("Logout Successful")
            navController.navigateScreen(
                Screen.Login.route,
                Screen.Main.route,
                true
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello $username") },
                actions = {
                    TextButton(onClick = { showLogoutDialog = true }) {
                        Text("Logout")
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {
                val currentDestination =
                    bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                navItems.forEach { (screen, icon) ->
                    NavigationBarItem(
                        selected = currentDestination == screen,
                        onClick = {
                            bottomNavController.navigate(screen) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                icon,
                                contentDescription = screen
                            )
                        },
                        label = {
                            Text(screen.replaceFirstChar {
                                it.uppercase()
                            })
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.List.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.List.route) {
                ListScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }

    if (showLogoutDialog) {
        SimpleDialog(
            "Confirm Logout",
            "Are you sure you want to logout",
            showOnChange = {
                showLogoutDialog = it
            },
            confirmOnClick = {
                viewModel.logout()
            }
        )
    }
}
