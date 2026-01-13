package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetDetailPokemonUseCaseTest {

    private val repository: PokemonRepository = mockk()
    private lateinit var useCase: GetDetailPokemonUseCase

    @Before
    fun setUp() {
        useCase = GetDetailPokemonUseCase(repository)
    }

    @Test
    fun `invoke returns Success when repository returns Success`() = runTest {
        val pokedexNumber = 1
        val detailPokemon = mockk<DetailPokemon>()
        val expectedResult = ResultWrapper.Success(detailPokemon)

        coEvery {
            repository.getDetailPokemon(pokedexNumber)
        } returns expectedResult

        val result = useCase(pokedexNumber)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(detailPokemon, result.data)

        coVerify(exactly = 1) {
            repository.getDetailPokemon(pokedexNumber)
        }
    }

    @Test
    fun `invoke returns Error when repository returns Error`() = runTest {
        val pokedexNumber = 2026
        val errorMessage = "Pokemon not found"
        val expectedResult = ResultWrapper.Error(errorMessage)

        coEvery {
            repository.getDetailPokemon(pokedexNumber)
        } returns expectedResult

        val result = useCase(pokedexNumber)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(errorMessage, result.message)

        coVerify(exactly = 1) {
            repository.getDetailPokemon(pokedexNumber)
        }
    }
}
