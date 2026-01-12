package juniar.nicolas.pokeapp.jetpackcompose.feature.splash

import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()

    @Test
    fun `navigate to Main when username is not empty`() = runTest {
        every {
            getLoggedUsernameUseCase()
        } returns flowOf("nico")

        val viewModel = SplashViewModel(getLoggedUsernameUseCase)

        val signal = viewModel.signal.first()

        assertEquals(
            SplashSignal.NavigateToMain,
            signal
        )
    }

    @Test
    fun `navigate to Login when username is empty`() = runTest {
        every {
            getLoggedUsernameUseCase()
        } returns flowOf("")

        val viewModel = SplashViewModel(getLoggedUsernameUseCase)

        val signal = viewModel.signal.first()

        assertEquals(
            SplashSignal.NavigateToLogin,
            signal
        )
    }
}
