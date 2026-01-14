package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.list

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.MainDispatcherRule
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.NoopListCallback
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.PokemonDiffCallback
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListPokemonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ListPokemonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getListPokemonUseCase: GetListPokemonUseCase = mockk()

    private lateinit var viewModel: ListPokemonViewModel

    @Test
    fun `pagingPokemon emits paging data from use case`() = runTest {
        val pokemonList = listOf(
            Pokemon("Bulbasaur",1),
            Pokemon("Ivysaur",2)
        )

        val pagingData = PagingData.from(pokemonList)

        every {
            getListPokemonUseCase()
        } returns flowOf(pagingData)

        viewModel = ListPokemonViewModel(getListPokemonUseCase)

        viewModel.pagingPokemon.test {
            val result = awaitItem()

            val differ = AsyncPagingDataDiffer(
                diffCallback = PokemonDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            differ.submitData(result)
            runCurrent()

            assertEquals(2, differ.itemCount)
            assertEquals("Bulbasaur", differ.getItem(0)?.name)
            assertEquals("Ivysaur", differ.getItem(1)?.name)

            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { getListPokemonUseCase() }
    }
}
