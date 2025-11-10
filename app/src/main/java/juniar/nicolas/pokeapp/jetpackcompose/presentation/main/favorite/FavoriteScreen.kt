package juniar.nicolas.pokeapp.jetpackcompose.presentation.main.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list.PokemonItem

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier, openDetail: () -> Unit = {}) {
    val items = List(10) { "Favorite #${it + 1}" }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            PokemonItem(Pokemon(item)) {
                openDetail.invoke()
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