package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<Pokemon>>
}