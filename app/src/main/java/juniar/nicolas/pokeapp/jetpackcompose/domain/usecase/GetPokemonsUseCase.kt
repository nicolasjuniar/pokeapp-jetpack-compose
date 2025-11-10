package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): List<Pokemon> {
        return repository.getPokemons()
    }
}