package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.PokemonRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.data.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import retrofit2.HttpException
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryImplTest {

    private val api: PokeApi = mockk()
    private val appDatabase: AppDatabase = mockk(relaxed = true)
    private val pokemonMapper: PokemonMapper = mockk()

    private lateinit var repository: PokemonRepositoryImpl

    @Test
    fun `getDetailPokemon returns Success when api call succeeds`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)

        repository = PokemonRepositoryImpl(
            api = api,
            appDatabase = appDatabase,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )

        val pokedexNumber = 1
        val response = mockk<DetailPokemonResponse>()
        val domain = mockk<DetailPokemon>()

        coEvery { api.getDetailPokemon(pokedexNumber) } returns response
        every { pokemonMapper.toDomain(response) } returns domain

        val result = repository.getDetailPokemon(pokedexNumber)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(domain, result.data)

        coVerify(exactly = 1) { api.getDetailPokemon(pokedexNumber) }
        verify(exactly = 1) { pokemonMapper.toDomain(response) }
    }

    @Test
    fun `getDetailPokemon returns Error when IOException happens`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)

        repository = PokemonRepositoryImpl(
            api = api,
            appDatabase = appDatabase,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )

        val pokedexNumber = 1
        val errorMessage = "Network error"

        coEvery { api.getDetailPokemon(pokedexNumber) } throws IOException(errorMessage)

        val result = repository.getDetailPokemon(pokedexNumber)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun `getDetailPokemon returns Error when HttpException happens`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)

        repository = PokemonRepositoryImpl(
            api = api,
            appDatabase = appDatabase,
            pokemonMapper = pokemonMapper,
            dispatcher = dispatcher
        )

        val pokedexNumber = 1
        val httpException = mockk<HttpException> {
            every { message } returns "404 Not Found"
        }

        coEvery { api.getDetailPokemon(pokedexNumber) } throws httpException

        val result = repository.getDetailPokemon(pokedexNumber)

        assertTrue(result is ResultWrapper.Error)
        assertEquals("404 Not Found", result.message)
    }
}
