package ru.rsprm.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.rsprm.network.Api
import ru.rsprm.provider.TrucksProvider
import ru.rsprm.provider.ValidatorProvider
import ru.rsprm.repository.TrucksRepository
import ru.rsprm.storage.Database
import javax.inject.Singleton

@Module
class Module(val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideTruckRepository(database: Database, api: Api): TrucksRepository {
        return TrucksRepository(context, database, api)
    }

    @Singleton
    @Provides
    fun provideTruckProvider(trucksRepository: TrucksRepository): TrucksProvider {
        return TrucksProvider(trucksRepository)
    }

    @Singleton
    @Provides
    fun provideValidatorProvider(): ValidatorProvider {
        return ValidatorProvider()
    }
}
