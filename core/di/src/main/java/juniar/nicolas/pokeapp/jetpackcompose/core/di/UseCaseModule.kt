package juniar.nicolas.pokeapp.jetpackcompose.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetIsDarkThemeUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.SaveLoggedUsernameUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSaveLoggedUsenameUseCase(
        sessionRepository: SessionRepository
    ) = SaveLoggedUsernameUseCase(sessionRepository)

    @Provides
    fun provideGetLoggedUsenameUseCase(
        sessionRepository: SessionRepository
    ) = GetLoggedUsernameUseCase(sessionRepository)

    @Provides
    fun provideGetIsDarkThemeUseCase(
        themeRepository: ThemeRepository
    ) = GetIsDarkThemeUseCase(themeRepository)
}
