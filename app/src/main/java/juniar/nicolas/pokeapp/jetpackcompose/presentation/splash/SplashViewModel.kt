package juniar.nicolas.pokeapp.jetpackcompose.presentation.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase
) : ViewModel() {

    var username: String? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            username = getLoggedUsernameUseCase().first()
        }
    }
}