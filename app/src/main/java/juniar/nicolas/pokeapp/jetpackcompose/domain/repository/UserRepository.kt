package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.UserEntity
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User

interface UserRepository {
    suspend fun getUsername(username: String, password: String): String?
    suspend fun insertUser(user: User)
    suspend fun getUserByUsername(username: String): UserEntity?
    suspend fun getUserProfilePicture(username: String): String
    suspend fun updateUserProfilePicture(uri: String, username: String)
    suspend fun updateUser(user: UserEntity)
}
