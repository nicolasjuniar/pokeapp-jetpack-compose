package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.common.hash
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: AppDispatcher
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapper<Unit> {
        return withContext(dispatcher.default) {
            val result = userRepository.getUsername(username, password.hash())
            if (result != null) {
                ResultWrapper.Success(Unit)
            } else {
                ResultWrapper.Error("Invalid Username or Password")
            }
        }
    }
}
