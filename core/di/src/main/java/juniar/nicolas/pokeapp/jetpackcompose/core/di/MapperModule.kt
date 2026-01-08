package juniar.nicolas.pokeapp.jetpackcompose.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.FavoriteMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.PokemonMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.UserMapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideUserMapper() = UserMapper()

    @Provides
    fun providePokemonMapper() = PokemonMapper()

    @Provides
    fun provideFavoriteMapper() = FavoriteMapper()
}
