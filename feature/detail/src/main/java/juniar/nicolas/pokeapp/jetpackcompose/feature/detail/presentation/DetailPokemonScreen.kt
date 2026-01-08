package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    pokedexNumber: Int,
    viewModel: DetailPokemonViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(pokedexNumber) {
        viewModel.onEvent(DetailPokemonEvent.SetPokedexNumber(pokedexNumber))
    }

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            if (it is DefaultSignal.ShowToast) {
                context.showToast(it.message)
            }
        }
    }

    DetailPokemonContent(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick
    )
}
