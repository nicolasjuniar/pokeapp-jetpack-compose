package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.ResultWrapper
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username:String,password:String): ResultWrapper<Unit> {
        val result = userRepository.getUsername(username, password)
        return if(result!=null) {
            ResultWrapper.Success(Unit)
        } else {
            ResultWrapper.Error("Invalid Username or Password")
        }
    }
}