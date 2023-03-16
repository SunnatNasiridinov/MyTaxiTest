package uz.mytaxi.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mytaxi.test.app.App
import uz.mytaxi.test.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @[Singleton Provides]
    fun getDataBase(): AppDatabase = AppDatabase.getInstance(App.instance)
}