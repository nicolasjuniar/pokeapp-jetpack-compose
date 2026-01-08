package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import juniar.nicolas.pokeapp.jetpackcompose.core.common.Constant.Companion.POKEMON_IMAGE_URL
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon

@Composable
fun ListPokemonContent(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Pokemon>,
    openDetailScreen: (pokedexNumber: Int) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

        if (isRefreshing) {
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(
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

@Composable
fun PokemonItem(pokemonName: String, pokedexNumber: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val painter =
                    rememberAsyncImagePainter(model = "$POKEMON_IMAGE_URL$pokedexNumber.png")
                Image(
                    painter = painter,
                    contentDescription = pokemonName,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(8.dp))
                Text("#${pokedexNumber}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    pokemonName,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}