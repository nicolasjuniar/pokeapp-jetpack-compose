package juniar.nicolas.pokeapp.jetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.FavoriteRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.ChangePasswordUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.CheckFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetListFavoritePokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetListPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateFavoriteUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateUserProfilePictureUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = LoginUseCase(userRepository, dispatcher)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = RegisterUseCase(userRepository, dispatcher)

    @Provides
    @Singleton
    fun provideGetListPokemonUseCase(
        pokemonRepository: PokemonRepository
    ) = GetListPokemonUseCase(pokemonRepository)

    @Provides
    @Singleton
    fun provideSaveLoggedUsenameUseCase(
        sessionRepository: SessionRepository
    ) = SaveLoggedUsernameUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideGetLoggedUsenameUseCase(
        sessionRepository: SessionRepository
    ) = GetLoggedUsernameUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideGetDetailPokemonUseCase(
        pokemonRepository: PokemonRepository
    ) = GetDetailPokemonUseCase(pokemonRepository)

    @Provides
    @Singleton
    fun provideCheckFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ) = CheckFavoriteUseCase(favoriteRepository)

    @Provides
    @Singleton
    fun provideUpdateFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ) = UpdateFavoriteUseCase(favoriteRepository)

    @Provides
    @Singleton
    fun provideGetListFavoritePokemonUseCase(
        favoriteRepository: FavoriteRepository
    ) = GetListFavoritePokemonUseCase(favoriteRepository)

    @Provides
    @Singleton
    fun provideGetUserProfilePictureUseCase(
        userRepository: UserRepository
    ) = GetUserProfilePictureUseCase(userRepository)

    @Provides
    @Singleton
    fun provideUpdateUserProfilePictureUseCase(
        userRepository: UserRepository
    ) = UpdateUserProfilePictureUseCase(userRepository)

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = ChangePasswordUseCase(userRepository, dispatcher)
}
