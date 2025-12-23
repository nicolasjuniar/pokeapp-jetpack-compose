package juniar.nicolas.pokeapp.jetpackcompose.presentation.detail

sealed interface DetailPokemonEvent {
    data class SetPokedexNumber(val id: Int) : DetailPokemonEvent
    data object ClickFavoriteIcon: DetailPokemonEvent
}
