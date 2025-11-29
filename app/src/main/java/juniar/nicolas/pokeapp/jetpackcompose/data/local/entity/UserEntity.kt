package juniar.nicolas.pokeapp.jetpackcompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,


    @ColumnInfo(name = "password")
    val password: String
)