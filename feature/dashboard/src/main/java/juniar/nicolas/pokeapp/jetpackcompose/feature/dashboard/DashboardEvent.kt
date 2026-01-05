package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard

sealed interface DashboardEvent {
    data object LogoutTextClick: DashboardEvent
    data object ConfirmLogoutClick: DashboardEvent
}
