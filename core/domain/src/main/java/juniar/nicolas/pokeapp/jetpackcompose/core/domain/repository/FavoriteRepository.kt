package juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getListFavoritePokemon(username: String): Flow<PagingData<Pokemon>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    suspend fun isFavorite(favorite: Favorite): Boolean
}
