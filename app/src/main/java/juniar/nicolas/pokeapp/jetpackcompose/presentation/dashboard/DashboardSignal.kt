package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard

sealed interface DashboardSignal {
    object ShowLogoutDialog: DashboardSignal
    object NavigateToLogin: DashboardSignal
}
