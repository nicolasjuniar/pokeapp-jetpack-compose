package juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getListPokemon(): Flow<PagingData<Pokemon>>
    suspend fun getDetailPokemon(pokedexNumber: Int): ResultWrapper<DetailPokemon>
}
