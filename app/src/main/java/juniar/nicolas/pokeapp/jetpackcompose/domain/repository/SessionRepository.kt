package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getLoggedUsername(): Flow<String>
    suspend fun saveLoggedUsername(username: String)
    suspend fun clearLoggedUsername()
}