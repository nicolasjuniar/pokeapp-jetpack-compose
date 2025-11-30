package juniar.nicolas.pokeapp.jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "favorites",
    primaryKeys = ["username", "pokemonId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index(value = ["username"]),
        Index(value = ["pokemonId"])
    ]
)
data class FavoriteEntity(
    val username: String,
    val pokemonId: Int
)
