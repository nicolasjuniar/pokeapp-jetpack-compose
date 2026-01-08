package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.LogoutUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<DashboardState, DashboardEvent, DashboardSignal>(DashboardState()) {

    init {
        viewModelScope.launch {
            val loggedUsername = getLoggedUsernameUseCase().first()
            setState { copy(username = loggedUsername) }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            sendSignal(DashboardSignal.NavigateToLogin)
            logoutUseCase()
        }
    }

    override fun handleEvent(event: DashboardEvent) {
        viewModelScope.launch {
            when (event) {
                is DashboardEvent.LogoutTextClick -> setState { copy(showLogoutDialog = true) }
                is DashboardEvent.ConfirmLogoutClick -> logout()
                is DashboardEvent.OnDismissLogoutDialog -> setState { copy(showLogoutDialog = false) }
            }
        }
    }
}
