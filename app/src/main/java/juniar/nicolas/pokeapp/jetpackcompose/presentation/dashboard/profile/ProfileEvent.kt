package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

sealed interface ProfileEvent {
    data object ChangePictureClicked : ProfileEvent
    data object DismissChangeProfilePictureBottomSheet : ProfileEvent
    data class UpdateImageUri(val imageUri: String) : ProfileEvent
}
