package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

sealed interface ProfileSignal {
    object showChangePasswordBottomSheet : ProfileSignal
    object successUpdateProfilePicture: ProfileSignal
}
