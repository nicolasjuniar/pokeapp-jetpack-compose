package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.domain

import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.common.hash
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: AppDispatcher
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapper<Unit> {
        return withContext(dispatcher.default) {
            val isUsernameUnique = userRepository.getUserByUsername(username) == null
            if (!isUsernameUnique) {
                return@withContext ResultWrapper.Error("Username already registered")
            }

            val user = User(username, password.hash())
            userRepository.insertUser(user)

            ResultWrapper.Success(Unit)
        }
    }
}
