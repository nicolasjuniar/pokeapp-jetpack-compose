package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain

import androidx.paging.PagingData
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
class GetListFavoritePokemonUseCaseTest {

    private val favoriteRepository: FavoriteRepository = mockk()
    private lateinit var useCase: GetListFavoritePokemonUseCase

    @Test
    fun `invoke delegates to repository and returns paging flow`() = runTest {
        val username = "nico"

        val pagingData = PagingData.from(
            listOf(
                Pokemon("Bulbasaur", 1),
                Pokemon("Charmander", 4)
            )
        )

        val flow = flowOf(pagingData)

        every {
            favoriteRepository.getListFavoritePokemon(username)
        } returns flow

        useCase = GetListFavoritePokemonUseCase(favoriteRepository)

        val resultFlow = useCase(username)

        assertSame(flow, resultFlow)

        coVerify(exactly = 1) {
            favoriteRepository.getListFavoritePokemon(username)
        }
    }
}
