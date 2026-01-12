package juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getIsDarkTheme(): Flow<Boolean>
    suspend fun updateIsDarkTheme(isDarkTheme: Boolean)
}