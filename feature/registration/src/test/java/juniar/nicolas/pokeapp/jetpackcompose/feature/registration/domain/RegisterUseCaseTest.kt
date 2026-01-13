package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.registration.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUseCaseTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private lateinit var dispatcher: TestAppDispatcher
    private lateinit var registerUseCase: RegisterUseCase


    @Test
    fun `invoke returns Error when username already registered`() = runTest {
        dispatcher = TestAppDispatcher(testScheduler)
        registerUseCase = RegisterUseCase(
            userRepository = userRepository,
            dispatcher = dispatcher
        )
        val username = "nico"
        val password = "asdf1234"
        val existingUser = User(username, "hashed")

        coEvery {
            userRepository.getUserByUsername(username)
        } returns existingUser

        val result = registerUseCase(username, password)

        assertEquals(
            ResultWrapper.Error("Username already registered"),
            result
        )

        coVerify(exactly = 0) {
            userRepository.insertUser(any())
        }
    }

    @Test
    fun `invoke returns Success when username is unique`() = runTest {
        dispatcher = TestAppDispatcher(testScheduler)
        registerUseCase = RegisterUseCase(
            userRepository = userRepository,
            dispatcher = dispatcher
        )
        val username = "nico"
        val password = "asdf1234"

        coEvery {
            userRepository.getUserByUsername(username)
        } returns null

        coEvery {
            userRepository.insertUser(any())
        } returns Unit

        val result = registerUseCase(username, password)

        assertEquals(
            ResultWrapper.Success(Unit),
            result
        )

        coVerify(exactly = 1) {
            userRepository.insertUser(
                match {
                    it.username == username &&
                            it.password.isNotBlank() // hashed password
                }
            )
        }
    }
}
