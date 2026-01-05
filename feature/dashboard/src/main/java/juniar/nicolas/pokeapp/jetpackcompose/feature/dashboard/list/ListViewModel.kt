package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetListPokemonUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getPokemonsUseCase: GetListPokemonUseCase,
) : ViewModel() {

    val pagingPokemon: Flow<PagingData<Pokemon>> = getPokemonsUseCase()
        .cachedIn(viewModelScope)
}
