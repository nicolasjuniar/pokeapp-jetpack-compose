package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserProfilePictureUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private lateinit var useCase: GetUserProfilePictureUseCase

    @Test
    fun `invoke returns profile picture from repository`() = runTest {
        val username = "nico"
        val profilePicture = "lokal.divais.potoporfil"

        coEvery {
            userRepository.getUserProfilePicture(username)
        } returns profilePicture

        useCase = GetUserProfilePictureUseCase(userRepository)

        val result = useCase(username)

        assertEquals(profilePicture, result)

        coVerify(exactly = 1) {
            userRepository.getUserProfilePicture(username)
        }
    }
}
