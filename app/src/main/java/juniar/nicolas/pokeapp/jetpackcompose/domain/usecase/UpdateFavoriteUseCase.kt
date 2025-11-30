package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Favorite
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.FavoriteRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favorite: Favorite) {
        if (favoriteRepository.isFavorite(favorite)) {
            favoriteRepository.deleteFavorite(favorite)
        } else {
            favoriteRepository.insertFavorite(favorite)
        }
    }
}