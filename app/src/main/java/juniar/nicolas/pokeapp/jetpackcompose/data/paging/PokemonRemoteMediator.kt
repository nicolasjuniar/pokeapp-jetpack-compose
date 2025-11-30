package juniar.nicolas.pokeapp.jetpackcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonRemoteKeys
import java.io.IOException

private const val PAGE_SIZE = 10

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokeApi: PokeApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val response = pokeApi.getPokemons(limit = PAGE_SIZE, offset = page * PAGE_SIZE)
            val results = response.results

            val endOfPagination = results.isEmpty()

            appDatabase.withTransaction {
                val dao = appDatabase.pokemonDao()
                val remoteKeysDao = appDatabase.remoteKeysDao()

                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    dao.clearAll()
                }

                val pokemonEntities = results.map { item ->
                    val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                    PokemonEntity(
                        id = id,
                        name = item.name,
                        url = item.url
                    )
                }

                val keys = pokemonEntities.map { entity ->
                    PokemonRemoteKeys(
                        pokemonId = entity.id,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (endOfPagination) null else page + 1
                    )
                }

                remoteKeysDao.insertAll(keys)
                dao.insertAll(pokemonEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (ioe: IOException) {
            return MediatorResult.Error(ioe)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { pokemon ->
            appDatabase.remoteKeysDao().getRemoteKeys(pokemon.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonEntity>): PokemonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                appDatabase.remoteKeysDao().getRemoteKeys(pokemon.id)
            }
    }
}
