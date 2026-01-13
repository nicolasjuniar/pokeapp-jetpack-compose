package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.common.hash
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordUseCaseTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private lateinit var dispatcher: TestAppDispatcher
    private lateinit var useCase: ChangePasswordUseCase

    @Test
    fun `returns error when old password is wrong`() = runTest {
        dispatcher = TestAppDispatcher(testScheduler)
        useCase = ChangePasswordUseCase(userRepository, dispatcher)

        val user = User(
            username = "nico",
            password = "asdf1234".hash()
        )

        coEvery { userRepository.getUserByUsername("nico") } returns user

        val result = useCase(
            username = "nico",
            oldPassword = "worongpaswort",
            newPassword = "niwpaswort"
        )

        assert(result is ResultWrapper.Error)
        assertEquals(
            "Wrong Old Password",
            (result as ResultWrapper.Error).message
        )

        coVerify(exactly = 0) { userRepository.updateUser(any()) }
    }

    @Test
    fun `returns error when old password equals new password`() = runTest {
        dispatcher = TestAppDispatcher(testScheduler)
        useCase = ChangePasswordUseCase(userRepository, dispatcher)

        val password = "sempaswort"
        val user = User(
            username = "nico",
            password = password.hash()
        )

        coEvery { userRepository.getUserByUsername("nico") } returns user

        val result = useCase(
            username = "nico",
            oldPassword = password,
            newPassword = password
        )

        assert(result is ResultWrapper.Error)
        assertEquals(
            "Old Password and New Password must be different",
            (result as ResultWrapper.Error).message
        )

        coVerify(exactly = 0) { userRepository.updateUser(any()) }
    }

    @Test
    fun `updates password and returns success when input is valid`() = runTest {
        dispatcher = TestAppDispatcher(testScheduler)
        useCase = ChangePasswordUseCase(userRepository, dispatcher)

        val oldPassword = "oltpaswort"
        val newPassword = "newpaswort"

        val user = User(
            username = "nico",
            password = oldPassword.hash()
        )

        coEvery { userRepository.getUserByUsername("nico") } returns user
        coEvery { userRepository.updateUser(any()) } returns Unit

        val result = useCase(
            username = "nico",
            oldPassword = oldPassword,
            newPassword = newPassword
        )

        assert(result is ResultWrapper.Success)

        coVerify {
            userRepository.updateUser(
                match {
                    it.username == "nico" &&
                            it.password == newPassword.hash()
                }
            )
        }
    }
}
