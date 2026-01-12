package juniar.nicolas.pokeapp.jetpackcompose.feature.login.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.common.hash
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.login.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest {

    private val userRepository: UserRepository = mockk()

    @Test
    fun `returns Success when username and password are valid`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val useCase = LoginUseCase(
            userRepository = userRepository,
            dispatcher = dispatcher
        )

        val username = "nico"
        val password = "asdf1234"

        coEvery {
            userRepository.getUsername(username, password.hash())
        } returns username

        val result = useCase(username, password)

        assertTrue(result is ResultWrapper.Success)

        coVerify(exactly = 1) {
            userRepository.getUsername(username, password.hash())
        }
    }

    @Test
    fun `returns Error when username or password are invalid`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val useCase = LoginUseCase(
            userRepository = userRepository,
            dispatcher = dispatcher
        )

        val username = "nico"
        val password = "wrong_password"

        coEvery {
            userRepository.getUsername(username, password.hash())
        } returns null

        val result = useCase(username, password)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(
            "Invalid Username or Password",
            result.message
        )

        coVerify(exactly = 1) {
            userRepository.getUsername(username, password.hash())
        }
    }
}
