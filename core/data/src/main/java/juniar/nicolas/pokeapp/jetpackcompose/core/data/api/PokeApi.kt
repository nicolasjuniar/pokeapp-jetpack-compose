package juniar.nicolas.pokeapp.jetpackcompose.core.data.api

import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): PokemonResponse

    @GET("pokemon/{pokedexNumber}")
    suspend fun getDetailPokemon(
        @Path("pokedexNumber") pokedexNumber: Int
    ): DetailPokemonResponse
}
