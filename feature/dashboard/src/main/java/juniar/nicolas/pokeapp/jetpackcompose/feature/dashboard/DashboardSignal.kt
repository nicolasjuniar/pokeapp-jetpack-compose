package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard

sealed interface DashboardSignal {
    data object ShowLogoutDialog: DashboardSignal
    data object NavigateToLogin: DashboardSignal
}
