package juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetPokemonsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getPokemonsUseCase: GetPokemonsUseCase,
) : ViewModel() {

    val pokemons: Flow<PagingData<PokemonEntity>> = getPokemonsUseCase()
        .cachedIn(viewModelScope)
}