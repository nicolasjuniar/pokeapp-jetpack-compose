package juniar.nicolas.pokeapp.jetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT username FROM users WHERE username = :username AND password = :password")
    suspend fun getUsername(username: String, password: String): String?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?
}
