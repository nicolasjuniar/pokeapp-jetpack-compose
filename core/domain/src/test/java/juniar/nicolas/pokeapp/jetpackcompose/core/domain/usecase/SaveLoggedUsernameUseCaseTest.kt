package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveLoggedUsernameUseCaseTest {

    private val repository: SessionRepository = mockk(relaxed = true)
    private val useCase = SaveLoggedUsernameUseCase(repository)

    @Test
    fun `save username to repository`() = runTest {
        val username = "nicolas"

        useCase(username)

        coVerify(exactly = 1) {
            repository.saveLoggedUsername(username)
        }
    }
}
