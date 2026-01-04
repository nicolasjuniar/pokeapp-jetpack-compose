package juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()
}
