package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogoutUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)
    private lateinit var useCase: LogoutUseCase

    @Test
    fun `invoke clears logged username`() = runTest {
        useCase = LogoutUseCase(sessionRepository)

        useCase()

        coVerify(exactly = 1) {
            sessionRepository.clearLoggedUsername()
        }
    }
}
