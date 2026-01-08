package juniar.nicolas.pokeapp.jetpackcompose.feature.login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.login.domain.LoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideLoginUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = LoginUseCase(userRepository, dispatcher)
}