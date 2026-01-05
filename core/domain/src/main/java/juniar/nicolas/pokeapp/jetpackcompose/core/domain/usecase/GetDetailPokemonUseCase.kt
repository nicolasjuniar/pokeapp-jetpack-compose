package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.common.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokedexNumber: Int): ResultWrapper<DetailPokemon> {
        return repository.getDetailPokemon(pokedexNumber)
    }
}