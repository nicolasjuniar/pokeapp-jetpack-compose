package juniar.nicolas.pokeappjetpackcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResult
import juniar.nicolas.pokeappjetpackcompose.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    val pokemons: Flow<PagingData<PokemonResult>> =
        repository.getPagedPokemons().cachedIn(viewModelScope)
}