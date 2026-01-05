package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.list.PokemonItem

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    openDetailScreen: (pokedexNumber: Int) -> Unit = {},
) {
    val pagingItems = viewModel.pagingPokemon.collectAsLazyPagingItems()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

        when {
            isRefreshing -> CircularProgressIndicator()
            pagingItems.itemCount == 0 -> Text(
                text = "Your favorites list is empty",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            )

            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = pagingItems.itemCount) { index ->
                    val item = pagingItems[index]
                    if (item != null) {
                        PokemonItem(item.name, item.pokedexNumber) {
                            openDetailScreen(item.pokedexNumber)
                        }
                    }
                }
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
