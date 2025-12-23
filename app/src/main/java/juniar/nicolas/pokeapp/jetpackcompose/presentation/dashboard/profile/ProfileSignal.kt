package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

sealed interface ProfileSignal {
    data object SuccessUpdateProfilePicture: ProfileSignal
}
