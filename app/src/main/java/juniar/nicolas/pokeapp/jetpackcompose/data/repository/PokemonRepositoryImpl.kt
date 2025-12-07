package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.paging.PokemonRemoteMediator
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi,
    private val appDatabase: AppDatabase,
    private val pokemonMapper: PokemonMapper,
    private val dispatcher: AppDispatcher
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getListPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, prefetchDistance = 2, enablePlaceholders = false
            ), remoteMediator = PokemonRemoteMediator(api, appDatabase), pagingSourceFactory = {
                appDatabase.pokemonDao().pagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map {
                pokemonMapper.toDomain(it)
            }
        }.flowOn(dispatcher.default)
    }

    override suspend fun getDetailPokemon(pokedexNumber: Int): ResultWrapper<DetailPokemon> {
        return withContext(dispatcher.io) {
            try {
                val result = api.getDetailPokemon(pokedexNumber)
                ResultWrapper.Success(pokemonMapper.toDomain(result))
            } catch (e: Exception) {
                ResultWrapper.Error(e.message.orEmpty())
            }
        }
    }
}
