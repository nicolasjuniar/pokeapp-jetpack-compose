package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile.changepassword

data class ChangePasswordState(
    val loggedUsername: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val changePasswordButtonEnabled: Boolean = false,
    val showChangePasswordBottomSheet:Boolean = false,
    val isLoading: Boolean = false
)
