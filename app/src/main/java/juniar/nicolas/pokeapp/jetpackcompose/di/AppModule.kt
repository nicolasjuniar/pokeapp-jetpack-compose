package juniar.nicolas.pokeapp.jetpackcompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import juniar.nicolas.pokeapp.jetpackcompose.data.api.PokeApi
import juniar.nicolas.pokeapp.jetpackcompose.data.datastore.SessionPreferences
import juniar.nicolas.pokeapp.jetpackcompose.data.local.AppDatabase
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.UserMapper
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
    fun provideSessionPreference(@ApplicationContext context: Context) =
        SessionPreferences(context)
}