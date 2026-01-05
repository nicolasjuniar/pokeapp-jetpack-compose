package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import androidx.paging.PagingData
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListFavoritePokemonUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(username: String): Flow<PagingData<Pokemon>> =
        favoriteRepository.getListFavoritePokemon(username)
}
