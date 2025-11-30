package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.paging.PokemonRemoteMediator
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi,
    private val appDatabase: AppDatabase,
    private val pokemonMapper: PokemonMapper
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, prefetchDistance = 2, enablePlaceholders = false
            ), remoteMediator = PokemonRemoteMediator(api, appDatabase), pagingSourceFactory = {
                appDatabase.pokemonDao().pagingSource()
            }).flow
    }

    override suspend fun getDetailPokemon(pokedexNumber: Int): ResultWrapper<DetailPokemon> {
        return try {
            val result = api.getDetailPokemon(pokedexNumber)
            ResultWrapper.Success(pokemonMapper.toDomain(result))
        } catch (e: Exception) {
            ResultWrapper.Error(e.message.orEmpty())
        }
    }
}