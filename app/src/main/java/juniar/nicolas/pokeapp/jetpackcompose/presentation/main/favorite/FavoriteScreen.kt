package juniar.nicolas.pokeapp.jetpackcompose.presentation.main.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list.PokemonItem
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val items = List(10) { "Favorite #${it + 1}" }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items) { index, value ->
            PokemonItem(value, index + 1) {
                navController.navigateScreen(Screen.Detail.createRoute(1))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    MaterialTheme {
        FavoriteScreen()
    }
}