package juniar.nicolas.pokeapp.jetpackcompose.core.shared

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetIsDarkThemeUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ThemeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase = mockk()

    @Test
    fun `isDarkTheme emits true when use case emits true`() = runTest {
        every { getIsDarkThemeUseCase() } returns flowOf(true)

        val viewModel = ThemeViewModel(getIsDarkThemeUseCase)

        viewModel.isDarkTheme.test {
            awaitItem()
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isDarkTheme remains false when use case emits false`() = runTest {
        every { getIsDarkThemeUseCase() } returns flowOf(false)

        val viewModel = ThemeViewModel(getIsDarkThemeUseCase)

        viewModel.isDarkTheme.test {
            assertFalse(awaitItem())
            expectNoEvents()
        }
    }
}
