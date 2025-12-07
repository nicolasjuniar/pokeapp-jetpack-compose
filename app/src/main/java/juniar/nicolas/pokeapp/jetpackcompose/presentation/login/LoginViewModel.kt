package juniar.nicolas.pokeapp.jetpackcompose.presentation.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.hash
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginSignal>(LoginState()) {

    private fun login() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            val username = state.value.username
            val password = state.value.password
            val result = loginUseCase.invoke(username, password)

            setState { copy(isLoading = false) }

            when (result) {
                is ResultWrapper.Success -> {
                    saveLoggedUsernameUseCase.invoke(username)
                    sendSignal(
                        listOf(
                            LoginSignal.ShowToast("Login Successful"),
                            LoginSignal.NavigateToMain
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    sendSignal(LoginSignal.ShowToast(result.message))
                }
            }
        }
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                setState {
                    copy(
                        username = event.value,
                        isButtonEnabled = event.value.isNotBlank() && password.isNotBlank()
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                setState {
                    copy(
                        password = event.value,
                        isButtonEnabled = username.isNotBlank() && event.value.isNotBlank()
                    )
                }
            }

            LoginEvent.LoginButtonClicked -> login()

            LoginEvent.RegisterTextClicked -> {
                viewModelScope.launch {
                    sendSignal(LoginSignal.NavigateToRegister)
                }
            }
        }
    }
}
