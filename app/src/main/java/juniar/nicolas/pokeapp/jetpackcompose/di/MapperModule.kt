package juniar.nicolas.pokeapp.jetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.FavoriteMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.UserMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideUserMapper() = UserMapper()

    @Provides
    @Singleton
    fun providePokemonMapper() = PokemonMapper()

    @Provides
    @Singleton
    fun provideFavoriteMapper() = FavoriteMapper()
}
