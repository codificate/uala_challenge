package com.challenge.uala.di

import android.content.Context
import com.challenge.uala.data.datasource.PlacesDatasource
import com.challenge.uala.data.repository.PlacesRepositoryImpl
import com.challenge.uala.domain.repository.PlacesRepository
import com.challenge.uala.domain.usecase.GetPlacesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UalaChallengeDiModule {
    @Provides
    fun providesDataSource(@ApplicationContext context: Context): PlacesDatasource {
        return PlacesDatasource(context.resources)
    }

    @Provides
    fun providesRepository(datasource: PlacesDatasource): PlacesRepository {
        return PlacesRepositoryImpl(datasource)
    }

    @Provides
    fun providesGetPlacesUseCase(repository: PlacesRepository): GetPlacesUseCase {
        return GetPlacesUseCase(repository)
    }

    @Provides
    fun providesFindByTextUseCase(repository: PlacesRepository): GetPlacesUseCase {
        return GetPlacesUseCase(repository)
    }
}