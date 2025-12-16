package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

sealed interface ProfileEvent {
    object ChangePictureClicked : ProfileEvent
    object ChangePasswordClicked : ProfileEvent
    object DismissChangeProfilePictureBottomSheet : ProfileEvent
    data class UpdateImageUri(val imageUri: String) : ProfileEvent
}
