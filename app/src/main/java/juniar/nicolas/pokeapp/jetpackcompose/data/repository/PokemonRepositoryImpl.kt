package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
) : PokemonRepository {

    override suspend fun getPokemons(): List<Pokemon> {
        return api.getPokemons().results.map {
            Pokemon(name = it.name.replaceFirstChar { char -> char.uppercase() })
        }
    }
}