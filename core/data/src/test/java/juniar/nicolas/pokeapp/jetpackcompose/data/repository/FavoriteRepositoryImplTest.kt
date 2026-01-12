package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.FavoriteDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.FavoriteEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.FavoriteMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.FavoriteRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.data.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepositoryImplTest {

    private val favoriteDao: FavoriteDao = mockk(relaxed = true)
    private val favoriteMapper: FavoriteMapper = mockk()
    private val pokemonMapper: PokemonMapper = mockk(relaxed = true)

    private lateinit var repository: FavoriteRepositoryImpl

    @Test
    fun `insertFavorite maps domain to entity and insert to dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        repository = FavoriteRepositoryImpl(
            favoriteDao = favoriteDao,
            favoriteMapper = favoriteMapper,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )
        val favorite = Favorite(username = "nico", pokemonId = 25)
        val entity = FavoriteEntity(username = "nico", pokemonId = 25)

        every { favoriteMapper.toEntity(favorite) } returns entity
        coEvery { favoriteDao.insert(entity) } returns Unit

        repository.insertFavorite(favorite)

        verify(exactly = 1) {
            favoriteMapper.toEntity(favorite)
        }
        coVerify(exactly = 1) {
            favoriteDao.insert(entity)
        }
    }

    @Test
    fun `deleteFavorite calls dao with correct parameters`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        repository = FavoriteRepositoryImpl(
            favoriteDao = favoriteDao,
            favoriteMapper = favoriteMapper,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )
        val favorite = Favorite(username = "nico", pokemonId = 25)

        coEvery {
            favoriteDao.delete("nico", 25)
        } returns Unit

        repository.deleteFavorite(favorite)

        coVerify(exactly = 1) {
            favoriteDao.delete("nico", 25)
        }
    }

    @Test
    fun `isFavorite returns true when dao returns true`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        repository = FavoriteRepositoryImpl(
            favoriteDao = favoriteDao,
            favoriteMapper = favoriteMapper,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )
        val favorite = Favorite(username = "nico", pokemonId = 25)

        coEvery {
            favoriteDao.isFavorite("nico", 25)
        } returns true

        val result = repository.isFavorite(favorite)

        assertTrue(result)
        coVerify(exactly = 1) {
            favoriteDao.isFavorite("nico", 25)
        }
    }

    @Test
    fun `isFavorite returns false when dao returns false`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        repository = FavoriteRepositoryImpl(
            favoriteDao = favoriteDao,
            favoriteMapper = favoriteMapper,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )
        val favorite = Favorite(username = "nico", pokemonId = 25)

        coEvery {
            favoriteDao.isFavorite("nico", 25)
        } returns false

        val result = repository.isFavorite(favorite)

        assertFalse(result)
    }
}
