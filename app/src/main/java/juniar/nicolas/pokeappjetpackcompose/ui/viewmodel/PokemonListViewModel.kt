package juniar.nicolas.pokeappjetpackcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import juniar.nicolas.pokeappjetpackcompose.data.remote.PokeApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val _pokemons = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val pokemons = _pokemons.asStateFlow()

    init {
        fetchPokemons()
    }

    private fun fetchPokemons() {
        viewModelScope.launch {
            try {
                val response = PokeApiClient.api.getPokemonList(limit = 10, offset = 0)
                val list = response.results.map {
                    val id = it.url.trimEnd('/').split("/").last().toIntOrNull() ?: 0
                    id to it.name.replaceFirstChar { c -> c.uppercaseChar() }
                }
                _pokemons.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
