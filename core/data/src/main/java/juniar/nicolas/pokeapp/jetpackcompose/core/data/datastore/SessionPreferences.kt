package juniar.nicolas.pokeapp.jetpackcompose.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "pokeapp_prefs")

class SessionPreferences(private val context: Context) {
    companion object {
        private val LOGGED_USERNAME = stringPreferencesKey("logged_username")
    }

    suspend fun saveLoggedUsername(username: String) {
        context.dataStore.edit {
            it[LOGGED_USERNAME] = username
        }
    }

    fun getLoggedUsername(): Flow<String> = context.dataStore.data.map {
        it[LOGGED_USERNAME] ?: ""
    }

    suspend fun clearLoggedUsername() {
        context.dataStore.edit {
            it[LOGGED_USERNAME] = ""
        }
    }
}
