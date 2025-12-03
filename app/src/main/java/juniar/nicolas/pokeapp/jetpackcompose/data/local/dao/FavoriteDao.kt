package juniar.nicolas.pokeapp.jetpackcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.FavoriteEntity
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity

@Dao
interface FavoriteDao {

    @Query("SELECT p.* FROM pokemons p " +
            "INNER JOIN favorites f " +
            "ON p.id = f.pokemonId " +
            "WHERE f.username = :username " +
            "ORDER BY p.id ASC")
    fun getFavoritePokemons(username: String): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE username = :username AND pokemonId = :pokemonId")
    suspend fun delete(username: String, pokemonId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE username = :username AND pokemonId = :pokemonId)")
    suspend fun isFavorite(username: String, pokemonId: Int): Boolean
}
