package juniar.nicolas.pokeapp.jetpackcompose.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object List : BottomNavItem("list", "List", Icons.Filled.List)
    object Favorite : BottomNavItem("favorite", "Favorite", Icons.Filled.Favorite)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.List) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(BottomNavItem.List, BottomNavItem.Favorite)
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem.route == item.route,
                        onClick = { selectedItem = item },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            is BottomNavItem.List -> ListScreen(Modifier.fillMaxSize())
            is BottomNavItem.Favorite -> FavoriteScreen(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "List Screen",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorite Screen",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationScreenPreview() {
    MaterialTheme {
        BottomNavigationScreen()
    }
}
