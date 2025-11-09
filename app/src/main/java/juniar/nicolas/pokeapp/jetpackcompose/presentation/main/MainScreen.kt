package juniar.nicolas.pokeapp.jetpackcompose.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.favorite.FavoriteScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list.ListScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    val navItems = listOf(
        Screen.List.route to Icons.AutoMirrored.Filled.List,
        Screen.Favorite.route to Icons.Filled.Favorite
    )

    Scaffold(
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
                    openDetail = {
                        navController.navigate(Screen.Detail.route)
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    modifier = Modifier.fillMaxSize(),
                    openDetail = {
                        navController.navigate(Screen.Detail.route)
                    }
                )
            }
        }
    }
}