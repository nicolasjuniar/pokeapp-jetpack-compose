package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfilePictureUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String) = userRepository.getUserProfilePicture(username)
}
