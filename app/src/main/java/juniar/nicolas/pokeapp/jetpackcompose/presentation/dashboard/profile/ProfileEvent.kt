package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

sealed interface ProfileEvent {
    data object ChangePictureClicked : ProfileEvent
    data object ChangePasswordClicked : ProfileEvent
    data object DismissChangeProfilePictureBottomSheet : ProfileEvent
    data object DismissChangePasswordBottomSheet : ProfileEvent
    data class UpdateImageUri(val imageUri: String) : ProfileEvent
}
