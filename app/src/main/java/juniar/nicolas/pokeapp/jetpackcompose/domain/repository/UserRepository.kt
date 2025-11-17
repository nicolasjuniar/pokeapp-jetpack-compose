package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User

interface UserRepository {
    suspend fun getUsername(username: String, password: String): String?
    suspend fun insertUser(user: User)
    suspend fun isUsernameUnique(username: String): Boolean
}