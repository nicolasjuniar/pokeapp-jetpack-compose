package juniar.nicolas.pokeapp.jetpackcompose.presentation.common

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)
