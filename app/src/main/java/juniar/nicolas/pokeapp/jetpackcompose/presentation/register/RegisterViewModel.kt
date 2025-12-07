package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSignal>(RegisterState()) {

    fun register() {
        viewModelScope.launch {

            setState { copy(isLoading = true) }

            val username = state.value.username
            val password = state.value.password
            val result = registerUseCase.invoke(username, password)

            setState { copy(isLoading = false) }

            when (result) {
                is ResultWrapper.Success -> {
                    saveLoggedUsernameUseCase.invoke(username)
                    sendSignal(
                        listOf(
                            RegisterSignal.ShowToast("RegisterSuccessful"),
                            RegisterSignal.NavigateToMain
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    sendSignal(RegisterSignal.ShowToast(result.message))
                }
            }
        }
    }

    private fun updateIsButtonEnabled(state: RegisterState): Boolean {
        return state.username.isNotBlank() &&
                state.password.length >= 6 &&
                state.confirmPassword == state.password
    }

    private fun updateRegisterState(reducer: RegisterState.() -> RegisterState) {
        setState {
            val newState = reducer()
            newState.copy(isButtonEnabled = updateIsButtonEnabled(newState))
        }
    }

    override fun handleEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UsernameChanged -> {
                updateRegisterState {
                    copy(
                        username = event.value
                    )
                }
            }

            is RegisterEvent.PasswordChanged -> {
                updateRegisterState {
                    copy(
                        password = event.value
                    )
                }
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                updateRegisterState {
                    copy(
                        confirmPassword = event.value
                    )
                }
            }

            RegisterEvent.RegisterButtonClicked -> register()

            RegisterEvent.LoginTextClicked -> {
                viewModelScope.launch {
                    sendSignal(RegisterSignal.NavigateToLogin)
                }
            }
        }
    }
}
