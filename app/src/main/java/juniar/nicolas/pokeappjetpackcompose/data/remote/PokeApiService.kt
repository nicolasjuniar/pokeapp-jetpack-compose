package juniar.nicolas.pokeappjetpackcompose.data.remote

import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse
}