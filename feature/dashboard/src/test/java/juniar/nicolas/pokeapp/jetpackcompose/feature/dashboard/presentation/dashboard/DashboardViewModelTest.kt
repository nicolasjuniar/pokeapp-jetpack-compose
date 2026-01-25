package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.dashboard

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.LogoutUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk(relaxed = true)

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        every { getLoggedUsernameUseCase() } returns flowOf("nico")

        viewModel = DashboardViewModel(
            getLoggedUsernameUseCase,
            logoutUseCase
        )
    }

    @Test
    fun `init sets username from GetLoggedUsernameUseCase`() = runTest {
        advanceUntilIdle()

        assertEquals("nico", viewModel.state.value.username)
    }
}