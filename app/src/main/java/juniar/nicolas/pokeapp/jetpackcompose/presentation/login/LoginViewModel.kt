package juniar.nicolas.pokeapp.jetpackcompose.presentation.login

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.hash
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase
) : BaseViewModel() {

    private val _isLoginSuccess = MutableSharedFlow<Boolean>()
    val isLoginSuccess = _isLoginSuccess.asSharedFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onChangeUsername(value: String) {
        username = value
    }

    fun onChangePassword(value: String) {
        password = value
    }

    val isButtonEnabled = derivedStateOf {
        username.isNotBlank() && password.isNotBlank()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            showLoading()

            when (val result = loginUseCase.invoke(username, password.hash())) {
                is ResultWrapper.Success -> {
                    saveLoggedUsernameUseCase.invoke(username)
                    showMessage("Login Successful")
                    _isLoginSuccess.emit(true)
                    hideLoading()
                }

                is ResultWrapper.Error -> {
                    showMessage(result.message)
                    hideLoading()
                }
            }
        }
    }
}
