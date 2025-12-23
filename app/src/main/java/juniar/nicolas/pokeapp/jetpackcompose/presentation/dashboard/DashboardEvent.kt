package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard

sealed interface DashboardEvent {
    data object LogoutTextClick: DashboardEvent
    data object ConfirmLogoutClick: DashboardEvent
}
