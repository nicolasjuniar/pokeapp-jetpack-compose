package juniar.nicolas.pokeapp.jetpackcompose.feature.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.SaveLoggedUsernameUseCase
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
                    LoginSignal.ShowToast(result.message)
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
