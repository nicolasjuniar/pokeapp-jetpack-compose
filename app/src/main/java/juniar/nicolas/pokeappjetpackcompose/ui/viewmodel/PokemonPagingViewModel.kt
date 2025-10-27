package juniar.nicolas.pokeappjetpackcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResult
import juniar.nicolas.pokeappjetpackcompose.data.paging.PokemonPagingSource
import kotlinx.coroutines.flow.Flow

class PokemonPagingViewModel : ViewModel() {

    val pokemonFlow: Flow<PagingData<PokemonResult>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PokemonPagingSource() }
    ).flow.cachedIn(viewModelScope)
}