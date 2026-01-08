package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

sealed interface ProfileEvent {
    data object ChangePictureClicked : ProfileEvent
    data object ChangePasswordClicked : ProfileEvent
    data object DismissBottomSheet : ProfileEvent
    data class UpdateImageUri(val imageUri: String) : ProfileEvent
}
