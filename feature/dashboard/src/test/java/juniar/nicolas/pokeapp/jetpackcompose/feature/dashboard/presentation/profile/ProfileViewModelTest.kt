package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetIsDarkThemeUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.UpdateDarkThemeUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.UpdateUserProfilePictureUseCase
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
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getUserProfilePictureUseCase: GetUserProfilePictureUseCase = mockk()
    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase = mockk(relaxed = true)
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase = mockk()
    private val updateDarkThemeUseCase: UpdateDarkThemeUseCase = mockk(relaxed = true)

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        every { getLoggedUsernameUseCase.invoke() } returns flowOf("nico")
        coEvery { getUserProfilePictureUseCase("nico") } returns "uri://profile"
        every { getIsDarkThemeUseCase.invoke() } returns flowOf(false)

        viewModel = ProfileViewModel(
            getUserProfilePictureUseCase = getUserProfilePictureUseCase,
            getLoggedUsernameUseCase = getLoggedUsernameUseCase,
            updateUserProfilePictureUseCase = updateUserProfilePictureUseCase,
            getIsDarkThemeUseCase = getIsDarkThemeUseCase,
            updateDarkThemeUseCase = updateDarkThemeUseCase
        )

        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `init loads username, profile picture and theme`() {
        val state = viewModel.state.value

        assertEquals("nico", state.loggedUsername)
        assertEquals("uri://profile", state.imageUri)
        assertFalse(state.isDarkTheme)
    }

    @Test
    fun `change picture clicked shows change picture sheet`() {
        viewModel.onEvent(ProfileEvent.ChangePictureClicked)

        assertEquals(
            ProfileSheet.ChangeProfilePicture,
            viewModel.state.value.activeSheet
        )
    }

    @Test
    fun `change password clicked shows change password sheet`() {
        viewModel.onEvent(ProfileEvent.ChangePasswordClicked)

        assertEquals(
            ProfileSheet.ChangePassword,
            viewModel.state.value.activeSheet
        )
    }

    @Test
    fun `dismiss bottom sheet clears active sheet`() {
        viewModel.onEvent(ProfileEvent.ChangePictureClicked)
        viewModel.onEvent(ProfileEvent.DismissBottomSheet)

        assertNull(viewModel.state.value.activeSheet)
    }

    @Test
    fun `update image uri updates state, repository and emits toast`() = runTest {
        val signals = mutableListOf<DefaultSignal>()
        val job = launch {
            viewModel.signal.collect { signals.add(it) }
        }

        viewModel.onEvent(ProfileEvent.UpdateImageUri("uri://new"))

        advanceUntilIdle()

        assertEquals("uri://new", viewModel.state.value.imageUri)

        coVerify {
            updateUserProfilePictureUseCase.invoke("uri://new", "nico")
        }

        assertTrue(
            signals.contains(
                DefaultSignal.ShowToast("Success Update Profile Picture")
            )
        )

        job.cancel()
    }

    @Test
    fun `update image uri does not call usecase when username empty`() = runTest {
        every { getLoggedUsernameUseCase.invoke() } returns flowOf("")

        viewModel = ProfileViewModel(
            getUserProfilePictureUseCase,
            getLoggedUsernameUseCase,
            updateUserProfilePictureUseCase,
            getIsDarkThemeUseCase,
            updateDarkThemeUseCase
        )

        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.onEvent(ProfileEvent.UpdateImageUri("uri://new"))

        advanceUntilIdle()

        coVerify(exactly = 0) {
            updateUserProfilePictureUseCase.invoke(any(), any())
        }
    }

    @Test
    fun `update dark theme updates state and call usecase`() = runTest {
        viewModel.onEvent(ProfileEvent.UpdateIsDarkTheme(true))

        advanceUntilIdle()

        assertTrue(viewModel.state.value.isDarkTheme)

        coVerify {
            updateDarkThemeUseCase.invoke(true)
        }
    }
}
