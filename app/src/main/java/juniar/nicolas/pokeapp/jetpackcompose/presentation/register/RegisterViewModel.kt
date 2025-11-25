package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.hash
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.ResultWrapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase
) : BaseViewModel() {

    private val _isRegisterSuccess = MutableSharedFlow<Boolean>()
    val isRegisterSuccess = _isRegisterSuccess.asSharedFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    fun onChangeUsername(value: String) {
        username = value
    }

    fun onChangePassword(value: String) {
        password = value
    }

    fun onChangeConfirmPassword(value: String) {
        confirmPassword = value
    }

    val isButtonEnabled = derivedStateOf {
        username.isNotBlank()
                && password.length >= 6
                && confirmPassword == password
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            showLoading()

            when (val result = registerUseCase.invoke(username, password.hash())) {
                is ResultWrapper.Success -> {
                    saveLoggedUsernameUseCase.invoke(username)
                    _isRegisterSuccess.emit(true)
                    showMessage("Register Successful")
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