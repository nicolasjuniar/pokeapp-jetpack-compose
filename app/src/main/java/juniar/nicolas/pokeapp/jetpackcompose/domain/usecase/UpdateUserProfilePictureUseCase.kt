package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfilePictureUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(uri: String, username: String) {
        userRepository.updateUserProfilePicture(uri, username)
    }
}
