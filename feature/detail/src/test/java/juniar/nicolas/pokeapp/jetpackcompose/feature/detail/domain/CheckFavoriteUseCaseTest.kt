package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CheckFavoriteUseCaseTest {

    private val favoriteRepository: FavoriteRepository = mockk()
    private lateinit var useCase: CheckFavoriteUseCase

    @Before
    fun setUp() {
        useCase = CheckFavoriteUseCase(favoriteRepository)
    }

    @Test
    fun `invoke returns true when pokemon is favorite`() = runTest {
        val username = "nico"
        val pokemonId = 25

        coEvery {
            favoriteRepository.isFavorite(Favorite(username, pokemonId))
        } returns true

        val result = useCase(username, pokemonId)

        assertTrue(result)

        coVerify(exactly = 1) {
            favoriteRepository.isFavorite(Favorite(username, pokemonId))
        }
    }

    @Test
    fun `invoke returns false when pokemon is not favorite`() = runTest {
        val username = "nico"
        val pokemonId = 25

        coEvery {
            favoriteRepository.isFavorite(Favorite(username, pokemonId))
        } returns false

        val result = useCase(username, pokemonId)

        assertFalse(result)

        coVerify(exactly = 1) {
            favoriteRepository.isFavorite(Favorite(username, pokemonId))
        }
    }
}
