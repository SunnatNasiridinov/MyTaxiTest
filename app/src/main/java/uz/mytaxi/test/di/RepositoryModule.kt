package uz.mytaxi.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mytaxi.test.domain.repository.LocationRepository
import uz.mytaxi.test.domain.repository.LocationRepositoryImpl
import uz.mytaxi.test.domain.usecase.LocationUseCase
import uz.mytaxi.test.domain.usecase.LocationUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun getLocationRepo(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @[Binds Singleton]
    fun getLocationUseCase(locationUseCaseImpl: LocationUseCaseImpl): LocationUseCase
}