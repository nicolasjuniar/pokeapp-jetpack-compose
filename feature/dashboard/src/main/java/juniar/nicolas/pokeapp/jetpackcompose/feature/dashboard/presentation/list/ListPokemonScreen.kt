package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListPokemonViewModel = hiltViewModel(),
    openDetailScreen: (pokedexNumber: Int) -> Unit = {},
) {
    val pagingItems = viewModel.pagingPokemon.collectAsLazyPagingItems()

    ListPokemonContent(
        modifier = modifier,
        pagingItems = pagingItems,
        openDetailScreen = openDetailScreen
    )
}

@Preview
@Composable
fun ListScreenPreview() {
    ListScreen()
}
