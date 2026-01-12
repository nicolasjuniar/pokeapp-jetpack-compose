package juniar.nicolas.pokeapp.jetpackcompose.core.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.core.data.datastore.DataStorePreference
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : SessionRepository {

    override fun getLoggedUsername(): Flow<String> = dataStorePreference.getLoggedUsername()

    override suspend fun saveLoggedUsername(username: String) {
        dataStorePreference.saveLoggedUsername(username)
    }

    override suspend fun clearLoggedUsername() {
        dataStorePreference.clearLoggedUsername()
    }
}
