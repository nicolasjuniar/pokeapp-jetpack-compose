package juniar.nicolas.pokeappjetpackcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResponse
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResult
import juniar.nicolas.pokeappjetpackcompose.data.network.ApiClient
import juniar.nicolas.pokeappjetpackcompose.data.paging.PokemonPagingSource
import kotlinx.coroutines.flow.Flow

class PokemonRepository {

    fun getPagedPokemons(): Flow<PagingData<PokemonResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource() }
        ).flow
    }
}