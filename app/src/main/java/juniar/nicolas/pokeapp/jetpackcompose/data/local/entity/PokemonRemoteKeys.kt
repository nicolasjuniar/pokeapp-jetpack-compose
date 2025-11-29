package juniar.nicolas.pokeapp.jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_keys")
data class PokemonRemoteKeys(
    @PrimaryKey
    val pokemonId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)