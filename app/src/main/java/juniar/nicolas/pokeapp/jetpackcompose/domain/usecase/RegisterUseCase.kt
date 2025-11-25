package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.ResultWrapper
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapper<Unit> {
        val isUnique = userRepository.isUsernameUnique(username)

        if (!isUnique) {
            return ResultWrapper.Error("Username already registered")
        }

        val user = User(username, password)
        userRepository.insertUser(user)

        return ResultWrapper.Success(Unit)
    }
}