package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

sealed interface DashboardSignal {
    data object NavigateToLogin: DashboardSignal
}
