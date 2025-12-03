package juniar.nicolas.pokeapp.jetpackcompose.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.FavoriteEntity
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Favorite

class FavoriteMapper {

    fun toDomain(favoriteEntity: FavoriteEntity) =
        Favorite(favoriteEntity.username, favoriteEntity.pokemonId)

    fun toEntity(favorite: Favorite) = FavoriteEntity(favorite.username, favorite.pokemonId)
}
