package juniar.nicolas.pokeapp.jetpackcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE username = :username")
    fun getListFavoriteByUsername(username: String): PagingSource<Int, FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE username = :username AND pokemonId = :pokemonId")
    suspend fun delete(username: String, pokemonId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE username = :username AND pokemonId = :pokemonId)")
    suspend fun isFavorite(username: String, pokemonId: Int): Boolean
}