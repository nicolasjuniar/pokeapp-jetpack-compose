package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.changepassword

sealed interface ChangePasswordEvent {
    data class OldPasswordChanged(val value: String) : ChangePasswordEvent
    data class NewPasswordChanged(val value: String) : ChangePasswordEvent
    data class ConfirmPasswordChanged(val value: String) : ChangePasswordEvent
    data object ChangePasswordButtonClicked : ChangePasswordEvent
    data object ResetChangePasswordField: ChangePasswordEvent
}
