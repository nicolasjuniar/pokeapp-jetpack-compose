package juniar.nicolas.pokeapp.jetpackcompose.core.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.core.data.datastore.SessionPreferences
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionPreferences: SessionPreferences
) : SessionRepository {

    override fun getLoggedUsername(): Flow<String> = sessionPreferences.getLoggedUsername()

    override suspend fun saveLoggedUsername(username: String) {
        sessionPreferences.saveLoggedUsername(username)
    }

    override suspend fun clearLoggedUsername() {
        sessionPreferences.clearLoggedUsername()
    }
}
