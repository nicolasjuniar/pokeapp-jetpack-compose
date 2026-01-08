package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.favorite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: ListFavoritePokemonViewModel = hiltViewModel(),
    openDetailScreen: (pokedexNumber: Int) -> Unit = {},
) {
    val pagingItems = viewModel.pagingPokemon.collectAsLazyPagingItems()

    ListFavoritePokemonContent(
        modifier = modifier,
        pagingItems = pagingItems,
        openDetailScreen = openDetailScreen
    )
}
