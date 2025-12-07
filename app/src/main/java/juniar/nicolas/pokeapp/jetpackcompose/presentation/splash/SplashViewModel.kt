package juniar.nicolas.pokeapp.jetpackcompose.presentation.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase
) : BaseViewModel<Unit, Unit, SplashSignal>(Unit) {

    init {
        viewModelScope.launch {
            val username = getLoggedUsernameUseCase().first()

            if (username.isNotEmpty()) {
                sendSignal(SplashSignal.NavigateToMain)
            } else {
                sendSignal(SplashSignal.NavigateToLogin)
            }
        }
    }
}
