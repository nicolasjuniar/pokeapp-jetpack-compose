package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateUserProfilePictureUseCaseTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private lateinit var useCase: UpdateUserProfilePictureUseCase

    @Test
    fun `invoke updates user profile picture`() = runTest {
        val uri = "konten://imej/profil.pienji"
        val username = "nico"

        useCase = UpdateUserProfilePictureUseCase(userRepository)

        useCase(uri, username)

        coVerify(exactly = 1) {
            userRepository.updateUserProfilePicture(uri, username)
        }
    }
}
