package juniar.nicolas.pokeapp.jetpackcompose.core.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)