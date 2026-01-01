package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile.changepassword

sealed interface ChangePasswordSignal {
    data class ShowToast(val message: String) : ChangePasswordSignal
    data object DismissChangePasswordBottomSheet : ChangePasswordSignal
}
