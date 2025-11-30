package juniar.nicolas.pokeapp.jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
)