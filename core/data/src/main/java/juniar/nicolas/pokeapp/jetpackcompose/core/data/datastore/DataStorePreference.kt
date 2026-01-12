package juniar.nicolas.pokeapp.jetpackcompose.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "pokeapp_prefs")

class DataStorePreference(private val context: Context) {
    companion object {
        private val LOGGED_USERNAME = stringPreferencesKey("logged_username")
        private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    suspend fun saveLoggedUsername(username: String) {
        context.dataStore.edit {
            it[LOGGED_USERNAME] = username
        }
    }

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit {
            it[IS_DARK_THEME] = isDarkTheme
        }
    }

    fun getLoggedUsername(): Flow<String> = context.dataStore.data.map {
        it[LOGGED_USERNAME] ?: ""
    }

    fun getIsDarkTheme(): Flow<Boolean> = context.dataStore.data.map {
        it[IS_DARK_THEME] ?: false
    }

    suspend fun clearLoggedUsername() {
        context.dataStore.edit {
            it[LOGGED_USERNAME] = ""
        }
    }
}
