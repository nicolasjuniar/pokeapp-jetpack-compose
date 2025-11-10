package juniar.nicolas.pokeapp.jetpackcompose.presentation.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetPokemonsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private val _pokemons = MutableStateFlow<List<Pokemon>>(listOf())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    init {
        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch {
            try {
                _pokemons.value = getPokemonsUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}