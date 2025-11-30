package juniar.nicolas.pokeapp.jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val username: String,
    val password: String
)