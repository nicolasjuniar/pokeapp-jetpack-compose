package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.hash
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: AppDispatcher
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapper<Unit> {
        return withContext(dispatcher.default) {
            if (!userRepository.isUsernameUnique(username)) {
                return@withContext ResultWrapper.Error("Username already registered")
            }

            val user = User(username, password.hash())
            userRepository.insertUser(user)

            ResultWrapper.Success(Unit)
        }
    }
}
