package juniar.nicolas.pokeapp.jetpackcompose.feature.login.presentation

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.login.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.login.domain.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase: LoginUseCase = mockk()
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase =
        mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            saveLoggedUsernameUseCase = saveLoggedUsernameUseCase
        )
    }

    @Test
    fun `login success emits toast and navigate to main`() = runTest {
        coEvery {
            loginUseCase.invoke("nico", "asdf1234")
        } returns ResultWrapper.Success(Unit)

        viewModel.onEvent(LoginEvent.UsernameChanged("nico"))
        viewModel.onEvent(LoginEvent.PasswordChanged("asdf1234"))

        viewModel.signal.test {
            viewModel.onEvent(LoginEvent.LoginButtonClicked)

            assertEquals(
                LoginSignal.ShowToast("Login Successful"),
                awaitItem()
            )
            assertEquals(
                LoginSignal.NavigateToMain,
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            saveLoggedUsernameUseCase.invoke("nico")
        }
    }

    @Test
    fun `login failed emits error toast`() = runTest {
        coEvery {
            loginUseCase.invoke("nico", "wrong_password")
        } returns ResultWrapper.Error("Invalid Username or Password")

        viewModel.onEvent(LoginEvent.UsernameChanged("nico"))
        viewModel.onEvent(LoginEvent.PasswordChanged("wrong_password"))

        viewModel.signal.test {
            viewModel.onEvent(LoginEvent.LoginButtonClicked)

            assertEquals(
                LoginSignal.ShowToast("Invalid Username or Password"),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `register clicked emits navigate to register`() = runTest {
        viewModel.signal.test {
            viewModel.onEvent(LoginEvent.RegisterTextClicked)

            assertEquals(
                LoginSignal.NavigateToRegister,
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `username and password enable login button`() = runTest {
        viewModel.onEvent(LoginEvent.UsernameChanged("nico"))
        assertFalse(viewModel.state.value.isButtonEnabled)

        viewModel.onEvent(LoginEvent.PasswordChanged("asdf1234"))
        assertTrue(viewModel.state.value.isButtonEnabled)
    }
}
