package juniar.nicolas.pokeapp.jetpackcompose.feature.detail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.CheckFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.detail.domain.UpdateFavoriteUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DetailPokemonModule {

    @Provides
    fun provideGetDetailPokemonUseCase(
        pokemonRepository: PokemonRepository
    ) = GetDetailPokemonUseCase(pokemonRepository)

    @Provides
    fun provideCheckFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ) = CheckFavoriteUseCase(favoriteRepository)

    @Provides
    fun provideUpdateFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ) = UpdateFavoriteUseCase(favoriteRepository)
}