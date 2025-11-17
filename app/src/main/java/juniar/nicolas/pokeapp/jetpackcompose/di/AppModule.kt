package juniar.nicolas.pokeapp.jetpackcompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.local.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.UserMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.repository.PokemonRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.data.repository.UserRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.PokemonRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.LoginUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.RegisterUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun providePokemonApi(): PokeApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApi): PokemonRepository =
        PokemonRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pokeapp_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideUserMapper() = UserMapper()

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, userMapper: UserMapper): UserRepository =
        UserRepositoryImpl(userDao, userMapper)

    @Singleton
    fun provideLoginUseCase(
        userRepository: UserRepository
    ): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Singleton
    fun provideRegisterUseCase(
        userRepository: UserRepository
    ): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }
}