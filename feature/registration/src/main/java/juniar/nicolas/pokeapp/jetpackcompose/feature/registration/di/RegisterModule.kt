package juniar.nicolas.pokeapp.jetpackcompose.feature.registration.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.UserRepository
import juniar.nicolas.pokeapp.jetpackcompose.feature.registration.domain.RegisterUseCase

@Module
@InstallIn(ViewModelComponent::class)
object RegisterModule {

    @Provides
    fun provideRegisterUseCase(
        userRepository: UserRepository,
        dispatcher: AppDispatcher
    ) = RegisterUseCase(userRepository, dispatcher)
}