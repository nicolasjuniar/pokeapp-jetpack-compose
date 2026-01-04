package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(username: String, pokemonId: Int) {
        val favorite = Favorite(username, pokemonId)
        if (favoriteRepository.isFavorite(favorite)) {
            favoriteRepository.deleteFavorite(favorite)
        } else {
            favoriteRepository.insertFavorite(favorite)
        }
    }
}