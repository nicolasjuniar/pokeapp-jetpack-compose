package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.registration.domain.RegisterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationSignal>(RegistrationState()) {

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
                            RegistrationSignal.ShowToast("RegisterSuccessful"),
                            RegistrationSignal.NavigateToMain
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    sendSignal(RegistrationSignal.ShowToast(result.message))
                }
            }
        }
    }

    private fun updateIsButtonEnabled(state: RegistrationState): Boolean {
        return state.username.isNotBlank() &&
                state.password.length >= 6 &&
                state.confirmPassword == state.password
    }

    private fun updateRegisterState(reducer: RegistrationState.() -> RegistrationState) {
        setState {
            val newState = reducer()
            newState.copy(isButtonEnabled = updateIsButtonEnabled(newState))
        }
    }

    override fun handleEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UsernameChanged -> {
                updateRegisterState {
                    copy(
                        username = event.value
                    )
                }
            }

            is RegistrationEvent.PasswordChanged -> {
                updateRegisterState {
                    copy(
                        password = event.value
                    )
                }
            }

            is RegistrationEvent.ConfirmPasswordChanged -> {
                updateRegisterState {
                    copy(
                        confirmPassword = event.value
                    )
                }
            }

            RegistrationEvent.RegisterButtonClicked -> register()

            RegistrationEvent.LoginTextClicked -> {
                viewModelScope.launch {
                    sendSignal(RegistrationSignal.NavigateToLogin)
                }
            }
        }
    }
}
