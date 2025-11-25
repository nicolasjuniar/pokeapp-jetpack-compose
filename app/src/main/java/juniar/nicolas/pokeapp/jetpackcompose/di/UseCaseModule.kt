package juniar.nicolas.pokeapp.jetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetDetailPokemonUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetPokemonsUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.SaveLoggedUsernameUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        userRepository: UserRepository
    ) = LoginUseCase(userRepository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        userRepository: UserRepository
    ) = RegisterUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetPokemonsUseCase(
        pokemonRepository: PokemonRepository
    ) = GetPokemonsUseCase(pokemonRepository)

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
    fun providegetDetailPokemonUseCase(
        pokemonRepository: PokemonRepository
    ) = GetDetailPokemonUseCase(pokemonRepository)
}