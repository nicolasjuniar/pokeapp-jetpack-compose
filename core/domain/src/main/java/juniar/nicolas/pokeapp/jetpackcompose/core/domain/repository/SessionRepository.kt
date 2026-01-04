package juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getLoggedUsername(): Flow<String>
    suspend fun saveLoggedUsername(username: String)
    suspend fun clearLoggedUsername()
}