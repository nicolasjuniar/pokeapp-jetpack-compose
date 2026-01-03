package juniar.nicolas.pokeapp.jetpackcompose.feature.login

import androidx.lifecycle.viewModelScope
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel() : BaseViewModel<LoginState, LoginEvent, LoginSignal>(LoginState()) {

    private fun login() {

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
