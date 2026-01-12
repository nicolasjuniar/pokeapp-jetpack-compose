package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLoggedUsernameUseCaseTest {

    private val repository: SessionRepository = mockk()
    private val useCase = GetLoggedUsernameUseCase(repository)

    @Test
    fun `returns username when user is logged in`() = runTest {
        val username = "nico"
        every { repository.getLoggedUsername() } returns flowOf(username)

        useCase().test {
            assertEquals(username, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `returns empty string when user is not logged in`() = runTest {
        every { repository.getLoggedUsername() } returns flowOf("")

        useCase().test {
            assertEquals("", awaitItem())
            awaitComplete()
        }
    }
}
