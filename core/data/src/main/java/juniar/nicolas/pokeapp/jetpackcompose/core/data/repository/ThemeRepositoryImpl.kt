package juniar.nicolas.pokeapp.jetpackcompose.core.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.core.data.datastore.DataStorePreference
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : ThemeRepository {
    override fun getIsDarkTheme() = dataStorePreference.getIsDarkTheme()

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        dataStorePreference.updateIsDarkTheme(isDarkTheme)
    }

}