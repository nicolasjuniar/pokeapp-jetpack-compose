package juniar.nicolas.pokeapp.jetpackcompose.feature.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.common.orEmpty
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.CheckFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : BaseViewModel<DetailPokemonState, DetailPokemonEvent, DefaultSignal>(DetailPokemonState()) {

    val username = getLoggedUsernameUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    private val _pokedexNumber = MutableStateFlow<Int?>(null)

    private fun setPokedexNumber(id: Int) {
        _pokedexNumber.value = id
        getDetailPokemon()
        checkUserFavorite()
    }

    private suspend fun checkFavorite(user: String, id: Int) = checkFavoriteUseCase(user, id)

    private fun checkUserFavorite() {
        viewModelScope.launch {
            combine(username, _pokedexNumber) { user, id ->
                Pair(user, id)
            }.collect { (user, id) ->
                if (user.isNotEmpty() && id != null) {
                    val isFavorite = checkFavorite(user, id)
                    setState { copy(isFavorite = isFavorite) }
                }
            }
        }
    }

    private fun getDetailPokemon() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = getDetailPokemonUseCase.invoke(_pokedexNumber.value.orEmpty())
            setState { copy(isLoading = false) }

            when (result) {
                is ResultWrapper.Success -> {
                    setState { copy(detailPokemon = result.data) }
                }

                is ResultWrapper.Error -> {
                    sendSignal(DefaultSignal.ShowToast(result.message))
                }
            }
        }
    }

    private fun updateFavorite() {
        viewModelScope.launch {
            updateFavoriteUseCase(username.value, _pokedexNumber.value.orEmpty())
            val isFavorite = checkFavoriteUseCase(username.value, _pokedexNumber.value.orEmpty())
            setState { copy(isFavorite = isFavorite) }
            sendSignal(
                DefaultSignal.ShowToast(
                    if (isFavorite) {
                        "Success Add to Favorite"
                    } else {
                        "Success Remove from Favorite"
                    }
                )
            )
        }
    }

    override fun handleEvent(event: DetailPokemonEvent) {
        when (event) {
            is DetailPokemonEvent.SetPokedexNumber -> setPokedexNumber(event.id)
            is DetailPokemonEvent.ClickFavoriteIcon -> updateFavorite()
        }
    }
}
