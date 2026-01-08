package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListFavoritePokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListFavoritePokemonViewModel @Inject constructor(
    getListFavoritePokemonUseCase: GetListFavoritePokemonUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
) : ViewModel() {

    val username = getLoggedUsernameUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingPokemon: Flow<PagingData<Pokemon>> = username
        .filter { it.isNotEmpty() }
        .flatMapLatest { user ->
            getListFavoritePokemonUseCase(user)
        }
        .cachedIn(viewModelScope)
}
