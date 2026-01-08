package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

sealed interface DashboardEvent {
    data object LogoutTextClick: DashboardEvent
    data object ConfirmLogoutClick: DashboardEvent
    data object OnDismissLogoutDialog: DashboardEvent
}
