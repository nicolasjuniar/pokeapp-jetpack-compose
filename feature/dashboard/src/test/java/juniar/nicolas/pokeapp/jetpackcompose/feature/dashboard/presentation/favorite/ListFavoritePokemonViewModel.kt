package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.favorite

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.NoopListCallback
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.PokemonDiffCallback
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListFavoritePokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ListFavoritePokemonViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getListFavoritePokemonUseCase: GetListFavoritePokemonUseCase = mockk()
    private val getLoggedUsernameUseCase: GetLoggedUsernameUseCase = mockk()

    private lateinit var viewModel: ListFavoritePokemonViewModel

    @Before
    fun setup() {
        every { getLoggedUsernameUseCase.invoke() } returns flowOf("ash")

        val pokemonList = listOf(
            Pokemon("Bulbasaur", 1),
            Pokemon(name = "Ivysaur", 1),
        )

        every { getListFavoritePokemonUseCase.invoke("ash") } returns
                flowOf(PagingData.from(pokemonList))

        viewModel = ListFavoritePokemonViewModel(
            getListFavoritePokemonUseCase = getListFavoritePokemonUseCase,
            getLoggedUsernameUseCase = getLoggedUsernameUseCase
        )
    }

    @Test
    fun `emit paging data when username not empty`() = runTest {
        val differ = AsyncPagingDataDiffer(
            diffCallback = PokemonDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = dispatcherRule.dispatcher
        )

        val pagingData = viewModel.pagingPokemon.first()
        differ.submitData(pagingData)

        advanceUntilIdle()

        assertEquals(2, differ.itemCount)
        assertEquals("Bulbasaur", differ.getItem(0)?.name)
        assertEquals("Ivysaur", differ.getItem(1)?.name)
    }

}
