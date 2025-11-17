package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import javax.inject.Inject

sealed class RegisterResult {
    object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): RegisterResult {
        val isUnique = userRepository.isUsernameUnique(username)

        if (!isUnique) {
            return RegisterResult.Error("Username already registered")
        }

        val user = User(username, password)
        userRepository.insertUser(user)

        return RegisterResult.Success
    }
}