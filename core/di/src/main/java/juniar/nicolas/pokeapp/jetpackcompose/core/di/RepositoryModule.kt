package juniar.nicolas.pokeapp.jetpackcompose.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.FavoriteRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.PokemonRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.SessionRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.UserRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.FavoriteRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository
}
