package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile.changepassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.ChangePasswordUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewmodel @Inject constructor(
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent, ChangePasswordSignal>(
    ChangePasswordState()
) {

    init {
        viewModelScope.launch {
            val username = getLoggedUsernameUseCase().first()
            setState { copy(loggedUsername = username) }
        }
    }

    private fun changePassword() {
        viewModelScope.launch {
            val username = state.value.loggedUsername
            val oldPassword = state.value.oldPassword
            val newPassword = state.value.newPassword

            setState { copy(isLoading = true) }
            val result = changePasswordUseCase.invoke(username, oldPassword, newPassword)
            setState { copy(isLoading = false) }

            when (result) {
                is ResultWrapper.Success -> {
                    resetField()
                    sendSignal(
                        listOf(
                            ChangePasswordSignal.ShowToast("Change Password Successful"),
                            ChangePasswordSignal.DismissChangePasswordBottomSheet
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    sendSignal(ChangePasswordSignal.ShowToast(result.message))
                }
            }
        }
    }

    private fun updateIsButtonEnabled(state: ChangePasswordState): Boolean {
        return state.oldPassword.isNotBlank() &&
                state.newPassword.length >= 6 &&
                state.confirmPassword == state.newPassword
    }

    private fun updateChangePasswordState(reducer: ChangePasswordState.() -> ChangePasswordState) {
        setState {
            val newState = reducer()
            newState.copy(changePasswordButtonEnabled = updateIsButtonEnabled(newState))
        }
    }

    fun resetField() {
        setState {
            copy(
                oldPassword = "",
                newPassword = "",
                confirmPassword = "",
            )
        }
    }

    override fun handleEvent(event: ChangePasswordEvent) {
        when (event) {

            is ChangePasswordEvent.OldPasswordChanged -> {
                updateChangePasswordState { copy(oldPassword = event.value) }
            }

            is ChangePasswordEvent.NewPasswordChanged -> {
                updateChangePasswordState { copy(newPassword = event.value) }
            }

            is ChangePasswordEvent.ConfirmPasswordChanged -> {
                updateChangePasswordState { copy(confirmPassword = event.value) }
            }

            is ChangePasswordEvent.ChangePasswordButtonClicked -> {
                changePassword()
            }

            is ChangePasswordEvent.ResetChangePasswordField -> {
                resetField()
            }
        }
    }
}
