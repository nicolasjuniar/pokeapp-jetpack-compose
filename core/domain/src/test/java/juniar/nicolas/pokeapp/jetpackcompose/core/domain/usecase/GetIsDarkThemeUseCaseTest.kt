package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetIsDarkThemeUseCaseTest {

    private val repository: ThemeRepository = mockk()
    private val useCase = GetIsDarkThemeUseCase(repository)

    @Test
    fun `emit true when repository returns true`() = runTest {
        every { repository.getIsDarkTheme() } returns flowOf(true)

        useCase().test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emit false when repository returns false`() = runTest {
        every { repository.getIsDarkTheme() } returns flowOf(false)

        useCase().test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }
}
