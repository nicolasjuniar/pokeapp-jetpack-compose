package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import juniar.nicolas.pokeapp.jetpackcompose.data.local.dao.FavoriteDao
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.FavoriteMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val favoriteMapper: FavoriteMapper,
    private val pokemonMapper: PokemonMapper
) : FavoriteRepository {
    override fun getFavoritePokemons(username: String): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { favoriteDao.getFavoritePokemons(username) }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                pokemonMapper.toDomain(entity)
            }
        }
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        val entity = favoriteMapper.toEntity(favorite)
        favoriteDao.insert(entity)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite.username, favorite.pokemonId)
    }

    override suspend fun isFavorite(favorite: Favorite): Boolean {
        return favoriteDao.isFavorite(favorite.username, favorite.pokemonId)
    }
}