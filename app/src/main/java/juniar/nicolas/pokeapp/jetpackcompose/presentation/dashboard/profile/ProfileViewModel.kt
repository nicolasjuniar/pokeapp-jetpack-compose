package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.ChangePasswordUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.DefaultSignal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserProfilePictureUseCase: GetUserProfilePictureUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ProfileState, ProfileEvent, DefaultSignal>(ProfileState()) {


    init {
        viewModelScope.launch {
            val username = getLoggedUsernameUseCase().first()
            if (username.isNotEmpty()) {
                val uri = getUserProfilePictureUseCase.invoke(username)
                setState { copy(imageUri = uri, loggedUsername = username) }
            }
        }
    }

    private fun changePassword() {
        viewModelScope.launch {
            val username = state.value.loggedUsername
            val oldPassword = state.value.oldPassword
            val newPassword = state.value.newPassword

            setState { copy(isLoading = true) }
            val result = changePasswordUseCase.invoke(username, oldPassword, newPassword)
            Log.d("change password", "kepanggil doang")
            setState { copy(isLoading = false) }

            when (result) {
                is ResultWrapper.Success -> {
                    sendSignal(DefaultSignal.ShowToast("Change Password Successful"))
                    onDismissChangePasswordBottomSheet()
                }

                is ResultWrapper.Error -> {
                    sendSignal(DefaultSignal.ShowToast(result.message))
                }
            }
        }
    }

    private fun updateIsButtonEnabled(state: ProfileState): Boolean {
        return state.oldPassword.isNotBlank() &&
                state.newPassword.length >= 6 &&
                state.confirmPassword == state.newPassword
    }

    private fun updateChangePasswordState(reducer: ProfileState.() -> ProfileState) {
        setState {
            val newState = reducer()
            newState.copy(changePasswordButtonEnabled = updateIsButtonEnabled(newState))
        }
    }

    private fun onDismissChangePasswordBottomSheet() {
        setState {
            copy(
                oldPassword = "",
                newPassword = "",
                confirmPassword = "",
                showChangePasswordBottomSheet = false
            )
        }
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangePictureClicked -> {
                setState { copy(showChangeProfilePictureBottomSheet = true) }
            }

            is ProfileEvent.ChangePasswordClicked -> {
                viewModelScope.launch {
                    setState { copy(showChangePasswordBottomSheet = true) }
                }
            }

            is ProfileEvent.DismissChangeProfilePictureBottomSheet -> {
                setState { copy(showChangeProfilePictureBottomSheet = false) }
            }

            is ProfileEvent.DismissChangePasswordBottomSheet -> {
                onDismissChangePasswordBottomSheet()
            }

            is ProfileEvent.UpdateImageUri -> {
                viewModelScope.launch {
                    val uri = event.imageUri
                    setState {
                        copy(imageUri = uri)
                    }
                    if (state.value.loggedUsername.isEmpty()) return@launch
                    updateUserProfilePictureUseCase.invoke(uri, state.value.loggedUsername)
                    sendSignal(DefaultSignal.ShowToast("Success Update Profile Picture"))
                }
            }

            is ProfileEvent.OldPasswordChanged -> {
                updateChangePasswordState { copy(oldPassword = event.value) }
            }

            is ProfileEvent.NewPasswordChanged -> {
                updateChangePasswordState { copy(newPassword = event.value) }
            }

            is ProfileEvent.ConfirmPasswordChanged -> {
                updateChangePasswordState { copy(confirmPassword = event.value) }
            }

            is ProfileEvent.ChangePasswordButtonClicked -> {
                changePassword()
            }
        }
    }
}
