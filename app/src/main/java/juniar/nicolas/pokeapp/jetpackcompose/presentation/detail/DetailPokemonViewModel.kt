package juniar.nicolas.pokeapp.jetpackcompose.presentation.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.CheckFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : BaseViewModel() {
    private val _detailPokemon = MutableSharedFlow<DetailPokemon>()
    val detailPokemon = _detailPokemon.asSharedFlow()

    val username = getLoggedUsernameUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    private val _pokedexNumber = MutableStateFlow<Int?>(null)

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        viewModelScope.launch {
            combine(username, _pokedexNumber) { user, id ->
                Pair(user, id)
            }.collect { (user, id) ->
                if (user.isNotEmpty() && id != null) {
                    _isFavorite.value = checkFavoriteUseCase(
                        Favorite(username = user, pokemonId = id)
                    )
                }
            }
        }
    }

    fun setPokedexNumber(pokedexNumber: Int) {
        _pokedexNumber.value = pokedexNumber
    }

    fun getDetailPokemon(pokedexNumber: Int) {
        showLoading()
        viewModelScope.launch {
            when (val result = getDetailPokemonUseCase.invoke(pokedexNumber)) {
                is ResultWrapper.Success -> {
                    _detailPokemon.emit(result.data)
                    hideLoading()
                }

                is ResultWrapper.Error -> {
                    showMessage(result.message)
                    hideLoading()
                }
            }
        }
    }

    fun updateFavorite() {
        val user = username.value
        val id = _pokedexNumber.value ?: return

        viewModelScope.launch {
            val favorite = Favorite(user, id)
            updateFavoriteUseCase(favorite)

            _isFavorite.value = checkFavoriteUseCase(favorite)
        }
    }
}