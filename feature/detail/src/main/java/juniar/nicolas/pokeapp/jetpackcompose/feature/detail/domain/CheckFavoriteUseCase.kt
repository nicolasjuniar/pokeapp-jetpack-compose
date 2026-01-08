package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class CheckFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(username: String, pokemonId: Int) = favoriteRepository.isFavorite(
        Favorite(username, pokemonId)
    )
}