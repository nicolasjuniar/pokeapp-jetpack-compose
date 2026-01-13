package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import androidx.paging.PagingData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
class GetListPokemonUseCaseTest {

    private val pokemonRepository: PokemonRepository = mockk()
    private lateinit var useCase: GetListPokemonUseCase

    @Test
    fun `invoke delegates to repository and returns paging flow`() = runTest {
        // GIVEN
        val pagingData = PagingData.from(
            listOf(
                Pokemon( "Bulbasaur",1),
                Pokemon("Ivysaur",2)
            )
        )

        val flow = flowOf(pagingData)

        every {
            pokemonRepository.getListPokemon()
        } returns flow

        useCase = GetListPokemonUseCase(pokemonRepository)

        val resultFlow = useCase()

        assertSame(flow, resultFlow)

        verify(exactly = 1) {
            pokemonRepository.getListPokemon()
        }
    }
}
