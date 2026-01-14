package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.changepassword

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.ChangePasswordUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()
    private val changePasswordUseCase: ChangePasswordUseCase = mockk()

    private lateinit var viewModel: ChangePasswordViewmodel

    @Before
    fun setup() {
        every { getLoggedUsernameUseCase.invoke() } returns flowOf("nico")

        viewModel = ChangePasswordViewmodel(
            getLoggedUsernameUseCase = getLoggedUsernameUseCase,
            changePasswordUseCase = changePasswordUseCase
        )

        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `init sets logged username`() {
        assertEquals("nico", viewModel.state.value.loggedUsername)
    }

    @Test
    fun `button enabled when input valid`() {
        viewModel.onEvent(ChangePasswordEvent.OldPasswordChanged("old12345"))
        viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("new12345"))

        assertTrue(viewModel.state.value.changePasswordButtonEnabled)
    }

    @Test
    fun `button disabled when confirm password mismatch`() {
        viewModel.onEvent(ChangePasswordEvent.OldPasswordChanged("old12345"))
        viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("wrong"))

        assertFalse(viewModel.state.value.changePasswordButtonEnabled)
    }

    @Test
    fun `change password success resets field and emits signals`() = runTest {
        coEvery {
            changePasswordUseCase.invoke(
                username = "nico",
                oldPassword = "old12345",
                newPassword = "new12345"
            )
        } returns ResultWrapper.Success(Unit)

        val signals = mutableListOf<ChangePasswordSignal>()
        val job = launch {
            viewModel.signal.collect { signals.add(it) }
        }

        viewModel.onEvent(ChangePasswordEvent.OldPasswordChanged("old12345"))
        viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ChangePasswordButtonClicked)

        advanceUntilIdle()

        assertEquals("", viewModel.state.value.oldPassword)
        assertEquals("", viewModel.state.value.newPassword)
        assertEquals("", viewModel.state.value.confirmPassword)

        assertTrue(
            signals.contains(
                ChangePasswordSignal.ShowToast("Change Password Successful")
            )
        )
        assertTrue(
            signals.contains(
                ChangePasswordSignal.DismissChangePasswordBottomSheet
            )
        )

        job.cancel()
    }

    @Test
    fun `change password error emits error toast`() = runTest {
        coEvery {
            changePasswordUseCase.invoke(any(), any(), any())
        } returns ResultWrapper.Error("Wrong Old Password")

        val signals = mutableListOf<ChangePasswordSignal>()
        val job = launch {
            viewModel.signal.collect { signals.add(it) }
        }

        viewModel.onEvent(ChangePasswordEvent.OldPasswordChanged("wrong"))
        viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("new12345"))
        viewModel.onEvent(ChangePasswordEvent.ChangePasswordButtonClicked)

        advanceUntilIdle()

        assertTrue(
            signals.contains(
                ChangePasswordSignal.ShowToast("Wrong Old Password")
            )
        )

        job.cancel()
    }

    @Test
    fun `reset change password field clears input`() {
        viewModel.onEvent(ChangePasswordEvent.OldPasswordChanged("old"))
        viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged("new"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("new"))

        viewModel.onEvent(ChangePasswordEvent.ResetChangePasswordField)

        val state = viewModel.state.value
        assertEquals("", state.oldPassword)
        assertEquals("", state.newPassword)
        assertEquals("", state.confirmPassword)
    }
}
