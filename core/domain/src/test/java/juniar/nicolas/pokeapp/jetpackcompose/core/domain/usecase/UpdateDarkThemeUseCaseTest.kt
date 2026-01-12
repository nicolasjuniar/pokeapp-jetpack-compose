package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateDarkThemeUseCaseTest {

    private val repository: ThemeRepository = mockk(relaxed = true)
    private val useCase = UpdateDarkThemeUseCase(repository)

    @Test
    fun `update dark theme value in repository`() = runTest {
        val isDarkTheme = true

        useCase(isDarkTheme)

        coVerify(exactly = 1) {
            repository.updateIsDarkTheme(isDarkTheme)
        }
    }
}
