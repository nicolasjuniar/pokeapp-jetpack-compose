package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.ChangePasswordUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListFavoritePokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetListPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.LogoutUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.UpdateUserProfilePictureUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DashboardModule {

    @Provides
    fun provideLogoutUsecase(
        sessionRepository: SessionRepository
    ) = LogoutUseCase(sessionRepository)

    @Provides
    fun provideGetListPokemonUseCase(
        pokemonRepository: PokemonRepository
    ) = GetListPokemonUseCase(pokemonRepository)

    @Provides
    fun provideGetListFavoritePokemonUseCase(
        favoriteRepository: FavoriteRepository
    ) = GetListFavoritePokemonUseCase(favoriteRepository)

    @Provides
    fun provideGetUserProfilePictureUseCase(
        userRepository: UserRepository
    ) = GetUserProfilePictureUseCase(userRepository)

    @Provides
    fun provideUpdateUserProfilePictureUseCase(
        userRepository: UserRepository
    ) = UpdateUserProfilePictureUseCase(userRepository)

    @Provides
    fun provideChangePasswordUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = ChangePasswordUseCase(userRepository, dispatcher)
}