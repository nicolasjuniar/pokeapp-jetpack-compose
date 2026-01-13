package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.presentation

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.registration.domain.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RegistrationViewModelTest {

    private val registerUseCase: RegisterUseCase = mockk()
    private val saveLoggedUsernameUseCase: SaveLoggedUsernameUseCase = mockk(relaxed = true)

    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = RegistrationViewModel(
            registerUseCase = registerUseCase,
            saveLoggedUsernameUseCase = saveLoggedUsernameUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `register success emits toast and navigate`() = runTest {
        coEvery {
            registerUseCase.invoke("nico", "asdf1234")
        } returns ResultWrapper.Success(Unit)

        viewModel.onEvent(RegistrationEvent.UsernameChanged("nico"))
        viewModel.onEvent(RegistrationEvent.PasswordChanged("asdf1234"))
        viewModel.onEvent(RegistrationEvent.ConfirmPasswordChanged("asdf1234"))

        viewModel.signal.test {
            viewModel.onEvent(RegistrationEvent.RegisterButtonClicked)

            advanceUntilIdle()

            assertEquals(
                RegistrationSignal.ShowToast("RegisterSuccessful"),
                awaitItem()
            )
            assertEquals(
                RegistrationSignal.NavigateToMain,
                awaitItem()
            )

            coVerify { saveLoggedUsernameUseCase.invoke("nico") }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `register failed emits error toast`() = runTest {
        coEvery {
            registerUseCase.invoke("nico", "asdf1234")
        } returns ResultWrapper.Error("Username already registered")

        viewModel.onEvent(RegistrationEvent.UsernameChanged("nico"))
        viewModel.onEvent(RegistrationEvent.PasswordChanged("asdf1234"))
        viewModel.onEvent(RegistrationEvent.ConfirmPasswordChanged("asdf1234"))

        viewModel.signal.test {
            viewModel.onEvent(RegistrationEvent.RegisterButtonClicked)

            advanceUntilIdle()

            assertEquals(
                RegistrationSignal.ShowToast("Username already registered"),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `button enabled when input valid`() = runTest {
        viewModel.onEvent(RegistrationEvent.UsernameChanged("nico"))
        viewModel.onEvent(RegistrationEvent.PasswordChanged("asdf1234"))
        viewModel.onEvent(RegistrationEvent.ConfirmPasswordChanged("asdf1234"))

        assertTrue(viewModel.state.value.isButtonEnabled)
    }
}
