package juniar.nicolas.pokeappjetpackcompose.data.repository

import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResponse
import juniar.nicolas.pokeappjetpackcompose.data.network.ApiClient

class PokemonRepository {

    private val api = ApiClient.api

    suspend fun getPokemons(limit: Int = 10, offset: Int = 0): PokemonResponse {
        return api.getPokemons(limit, offset)
    }
}