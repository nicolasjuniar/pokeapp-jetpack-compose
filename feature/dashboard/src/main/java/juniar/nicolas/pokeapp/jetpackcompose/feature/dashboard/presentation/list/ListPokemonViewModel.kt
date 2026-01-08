package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListPokemonUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    getListPokemonUseCase: GetListPokemonUseCase,
) : ViewModel() {
    val pagingPokemon: Flow<PagingData<Pokemon>> = getListPokemonUseCase()
        .cachedIn(viewModelScope)
}
