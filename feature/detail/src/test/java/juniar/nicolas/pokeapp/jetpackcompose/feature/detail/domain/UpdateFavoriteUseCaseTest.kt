package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateFavoriteUseCaseTest {

    private val favoriteRepository: FavoriteRepository = mockk(relaxed = true)
    private lateinit var useCase: UpdateFavoriteUseCase

    @Before
    fun setUp() {
        useCase = UpdateFavoriteUseCase(favoriteRepository)
    }

    @Test
    fun `invoke deletes favorite when pokemon already favorited`() = runTest {
        val username = "ash"
        val pokemonId = 25
        val favorite = Favorite(username, pokemonId)

        coEvery {
            favoriteRepository.isFavorite(favorite)
        } returns true

        useCase(username, pokemonId)

        coVerify(exactly = 1) {
            favoriteRepository.isFavorite(favorite)
            favoriteRepository.deleteFavorite(favorite)
        }

        coVerify(exactly = 0) {
            favoriteRepository.insertFavorite(any())
        }
    }

    @Test
    fun `invoke inserts favorite when pokemon not yet favorited`() = runTest {
        val username = "misty"
        val pokemonId = 7
        val favorite = Favorite(username, pokemonId)

        coEvery {
            favoriteRepository.isFavorite(favorite)
        } returns false

        useCase(username, pokemonId)

        coVerify(exactly = 1) {
            favoriteRepository.isFavorite(favorite)
            favoriteRepository.insertFavorite(favorite)
        }

        coVerify(exactly = 0) {
            favoriteRepository.deleteFavorite(any())
        }
    }
}
