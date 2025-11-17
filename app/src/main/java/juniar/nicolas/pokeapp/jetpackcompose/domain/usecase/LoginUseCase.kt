package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import javax.inject.Inject

sealed class LoginResult {
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username:String,password:String): LoginResult {
        val result = userRepository.getUsername(username, password)
        return if(result!=null) {
            LoginResult.Success
        } else {
            LoginResult.Error("Invalid Username or Password")
        }
    }
}