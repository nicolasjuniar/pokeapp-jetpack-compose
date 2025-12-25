package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.hash
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: AppDispatcher
) {

    suspend operator fun invoke(
        username: String,
        oldPassword: String,
        newPassword: String
    ): ResultWrapper<Unit> {
        return withContext(dispatcher.default) {
            val user = userRepository.getUserByUsername(username)
            val hashOldPassword = oldPassword.hash()
            if (user?.password != hashOldPassword) {
                return@withContext ResultWrapper.Error("Wrong Old Password")
            }

            val hashNewPassword = newPassword.hash()
            if (hashOldPassword == hashNewPassword) {
                return@withContext ResultWrapper.Error(
                    "Old Password and New Password must be different"
                )
            }

            userRepository.updateUser(
                user.copy(password = hashNewPassword)
            )
            return@withContext ResultWrapper.Success(Unit)
        }
    }

}
