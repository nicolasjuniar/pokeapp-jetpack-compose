package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

sealed interface ProfileSheet {
    data object ChangeProfilePicture : ProfileSheet
    data object ChangePassword : ProfileSheet
}

data class ProfileState(
    val loggedUsername: String = "",
    val imageUri: String = "",
    val isLoading: Boolean = false,
    val activeSheet: ProfileSheet? = null
)
