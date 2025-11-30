package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokedexNumber: Int): ResultWrapper<DetailPokemon> {
        return repository.getDetailPokemon(pokedexNumber)
    }
}