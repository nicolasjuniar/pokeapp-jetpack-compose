package juniar.nicolas.pokeapp.jetpackcompose.feature.detail

sealed interface DetailPokemonEvent {
    data class SetPokedexNumber(val id: Int) : DetailPokemonEvent
    data object ClickFavoriteIcon: DetailPokemonEvent
}
