package juniar.nicolas.pokeapp.jetpackcompose.feature.detail

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon

data class DetailPokemonState(
    val detailPokemon: DetailPokemon? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false
)
