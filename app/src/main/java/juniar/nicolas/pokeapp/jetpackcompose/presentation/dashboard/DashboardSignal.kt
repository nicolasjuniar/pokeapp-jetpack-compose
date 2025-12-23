package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard

sealed interface DashboardSignal {
    data object ShowLogoutDialog: DashboardSignal
    data object NavigateToLogin: DashboardSignal
}
