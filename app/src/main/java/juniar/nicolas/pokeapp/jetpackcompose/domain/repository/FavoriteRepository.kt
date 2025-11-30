package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getListFavoriteByUsername(username: String): Flow<PagingData<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    suspend fun isFavorite(favorite: Favorite):Boolean
}