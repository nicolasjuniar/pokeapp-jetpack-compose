package juniar.nicolas.pokeapp.jetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.datastore.SessionPreferences
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.local.dao.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.UserMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.repository.PokemonRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.data.repository.SessionRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.data.repository.UserRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApi, database: AppDatabase): PokemonRepository =
        PokemonRepositoryImpl(api, database)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, userMapper: UserMapper): UserRepository =
        UserRepositoryImpl(userDao, userMapper)

    @Provides
    @Singleton
    fun provideSessionRepository(sessionPreferences: SessionPreferences): SessionRepository =
        SessionRepositoryImpl(sessionPreferences)
}