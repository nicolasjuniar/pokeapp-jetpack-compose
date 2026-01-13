package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.presentation

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.CheckFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.UpdateFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailPokemonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getDetailPokemonUseCase: GetDetailPokemonUseCase = mockk()
    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()
    private val checkFavoriteUseCase: CheckFavoriteUseCase = mockk()
    private val updateFavoriteUseCase: UpdateFavoriteUseCase = mockk()

    private lateinit var viewModel: DetailPokemonViewModel

    @Test
    fun `SetPokedexNumber loads pokemon detail and updates state`() = runTest {
        val pokemonId = 1
        val detailPokemon = mockk<DetailPokemon>()

        every { getLoggedUsernameUseCase() } returns flowOf("nico")

        coEvery {
            getDetailPokemonUseCase(pokemonId)
        } returns ResultWrapper.Success(detailPokemon)

        coEvery {
            checkFavoriteUseCase("nico", pokemonId)
        } returns false

        viewModel = DetailPokemonViewModel(
            getDetailPokemonUseCase,
            getLoggedUsernameUseCase,
            checkFavoriteUseCase,
            updateFavoriteUseCase
        )

        viewModel.onEvent(DetailPokemonEvent.SetPokedexNumber(pokemonId))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(detailPokemon, state.detailPokemon)
        assertEquals(false, state.isFavorite)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `SetPokedexNumber sets isFavorite when pokemon is favorited`() = runTest {
        every { getLoggedUsernameUseCase() } returns flowOf("nico")

        coEvery {
            getDetailPokemonUseCase(any())
        } returns ResultWrapper.Success(mockk())

        coEvery {
            checkFavoriteUseCase("nico", 2)
        } returns true

        viewModel = DetailPokemonViewModel(
            getDetailPokemonUseCase,
            getLoggedUsernameUseCase,
            checkFavoriteUseCase,
            updateFavoriteUseCase
        )

        viewModel.onEvent(DetailPokemonEvent.SetPokedexNumber(2))
        advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isFavorite)
    }

    @Test
    fun `ClickFavoriteIcon adds favorite and shows success toast`() = runTest {
        every { getLoggedUsernameUseCase() } returns flowOf("nico")

        coEvery {
            getDetailPokemonUseCase(3)
        } returns ResultWrapper.Success(mockk())

        coEvery {
            checkFavoriteUseCase("nico", 3)
        } returnsMany listOf(false, true)

        coEvery {
            updateFavoriteUseCase("nico", 3)
        } just Runs

        viewModel = DetailPokemonViewModel(
            getDetailPokemonUseCase,
            getLoggedUsernameUseCase,
            checkFavoriteUseCase,
            updateFavoriteUseCase
        )

        val signals = mutableListOf<DefaultSignal>()
        val job = launch { viewModel.signal.collect { signals.add(it) } }

        viewModel.onEvent(DetailPokemonEvent.SetPokedexNumber(3))
        advanceUntilIdle()

        viewModel.onEvent(DetailPokemonEvent.ClickFavoriteIcon)
        advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isFavorite)
        assert(signals.any { it is DefaultSignal.ShowToast })

        job.cancel()
    }

    @Test
    fun `shows toast when getDetailPokemon fails`() = runTest {
        every { getLoggedUsernameUseCase() } returns flowOf("nico")

        coEvery {
            getDetailPokemonUseCase(2026)
        } returns ResultWrapper.Error("Not Found")

        coEvery {
            checkFavoriteUseCase("nico", 2026)
        } returns false

        viewModel = DetailPokemonViewModel(
            getDetailPokemonUseCase,
            getLoggedUsernameUseCase,
            checkFavoriteUseCase,
            updateFavoriteUseCase
        )

        val signals = mutableListOf<DefaultSignal>()
        val job = launch {
            viewModel.signal.collect { signals.add(it) }
        }

        viewModel.onEvent(DetailPokemonEvent.SetPokedexNumber(2026))
        advanceUntilIdle()

        assert(signals.contains(DefaultSignal.ShowToast("Not Found")))

        job.cancel()
    }

}
