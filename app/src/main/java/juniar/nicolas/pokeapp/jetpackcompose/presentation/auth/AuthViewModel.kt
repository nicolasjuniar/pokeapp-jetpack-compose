package juniar.nicolas.pokeapp.jetpackcompose.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginResult
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterResult
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthMode {
    LOGIN, REGISTER
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var authMode by mutableStateOf(AuthMode.LOGIN)
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set


    fun onChangeAuthMode(value: AuthMode) {
        authMode = value
    }

    fun onChangeUsername(value: String) {
        username = value
    }

    fun onChangePassword(value: String) {
        password = value
    }

    fun onChangeConfirmPassword(value: String) {
        confirmPassword = value
    }

    fun isValid(): Boolean {
        val usernameValid = username.isNotBlank()
        val passwordValid = if (authMode == AuthMode.LOGIN) {
            password.isNotBlank()
        } else {
            password.length >= 6
        }
        val confirmPasswordValid = if (authMode == AuthMode.LOGIN) {
            true
        } else {
            confirmPassword == password
        }
        return usernameValid && passwordValid && confirmPasswordValid
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            when (val result = registerUseCase.invoke(username, password)) {
                is RegisterResult.Success ->
                    _authState.value = AuthState.Success("Register Successful")

                is RegisterResult.Error ->
                    _authState.value = AuthState.Error(result.message)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            when (val result = loginUseCase.invoke(username, password)) {
                is LoginResult.Success ->
                    _authState.value = AuthState.Success("Login Successful")

                is LoginResult.Error ->
                    _authState.value = AuthState.Error(result.message)
            }
        }
    }

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val message: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }
}