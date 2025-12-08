package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard

sealed interface DashboardEvent {
    object LogoutTextClick: DashboardEvent
    object ConfirmLogoutClick: DashboardEvent
}
