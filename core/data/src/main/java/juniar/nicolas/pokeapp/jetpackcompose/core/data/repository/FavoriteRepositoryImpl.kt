package juniar.nicolas.pokeapp.jetpackcompose.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.FavoriteDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.FavoriteMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val favoriteMapper: FavoriteMapper,
    private val pokemonMapper: PokemonMapper,
    private val dispatcher: AppDispatcher
) : FavoriteRepository {
    override fun getListFavoritePokemon(username: String): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { favoriteDao.getListFavoritePokemon(username) }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                pokemonMapper.toDomain(entity)
            }
        }.flowOn(dispatcher.default)
    }

    override suspend fun insertFavorite(favorite: Favorite) =
        withContext(dispatcher.io) {
            val entity = favoriteMapper.toEntity(favorite)
            favoriteDao.insert(entity)
        }

    override suspend fun deleteFavorite(favorite: Favorite) =
        withContext(dispatcher.io) {
            favoriteDao.delete(favorite.username, favorite.pokemonId)
        }

    override suspend fun isFavorite(favorite: Favorite): Boolean =
        withContext(dispatcher.io) {
            favoriteDao.isFavorite(favorite.username, favorite.pokemonId)
        }
}
