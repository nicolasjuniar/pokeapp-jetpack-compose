package juniar.nicolas.pokeapp.jetpackcompose.presentation.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase
) : BaseViewModel() {
    private val _detailPokemon = MutableSharedFlow<DetailPokemon>()
    val detailPokemon = _detailPokemon.asSharedFlow()

    fun getDetailPokemon(pokemonName: String) {
        showLoading()
        viewModelScope.launch {
            when (val result = getDetailPokemonUseCase.invoke(pokemonName)) {
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
}