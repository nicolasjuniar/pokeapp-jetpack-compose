package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

data class ProfileState(
    val loggedUsername: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val changePasswordButtonEnabled: Boolean = false,
    val imageUri: String = "",
    val showChangeProfilePictureBottomSheet: Boolean = false,
    val showChangePasswordBottomSheet: Boolean = false,
    val isLoading:Boolean = false
)
