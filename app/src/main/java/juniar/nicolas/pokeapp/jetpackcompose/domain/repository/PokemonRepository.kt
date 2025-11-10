package juniar.nicolas.pokeapp.jetpackcompose.domain.repository

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemons(): List<Pokemon>
}