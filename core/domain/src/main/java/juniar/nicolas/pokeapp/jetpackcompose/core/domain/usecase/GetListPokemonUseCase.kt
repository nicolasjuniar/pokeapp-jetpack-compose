package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> = pokemonRepository.getListPokemon()
}
