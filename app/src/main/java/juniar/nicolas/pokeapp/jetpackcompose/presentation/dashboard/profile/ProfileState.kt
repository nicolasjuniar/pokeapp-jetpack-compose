package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

data class ProfileState(
    val loggedUsername: String = "",
    val imageUri: String = "",
    val showChangeProfilePictureBottomSheet: Boolean = false,
)
