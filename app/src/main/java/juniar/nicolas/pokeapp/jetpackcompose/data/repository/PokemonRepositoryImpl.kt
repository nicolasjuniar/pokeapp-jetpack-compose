package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.toDomain
import juniar.nicolas.pokeapp.jetpackcompose.data.paging.PokemonPagingSource
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
) : PokemonRepository {

    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        val pageSize = 10
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(api, pageSize) }).flow
    }

    override suspend fun getDetailPokemon(pokemonName: String): ResultWrapper<DetailPokemon> {
        return try {
            val result = api.getDetailPokemon(pokemonName)
            ResultWrapper.Success(result.toDomain())
        } catch (e: Exception) {
            ResultWrapper.Error(e.message.orEmpty())
        }
    }
}