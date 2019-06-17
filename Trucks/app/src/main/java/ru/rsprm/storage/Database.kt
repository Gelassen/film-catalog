package ru.rsprm.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.rsprm.storage.dao.TruckDao
import ru.rsprm.storage.model.TruckEntity

@androidx.room.Database(entities = [TruckEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract val truckDao: TruckDao

    companion object {

        @Volatile
        private var INSTANCE: Database? = null

        @Synchronized
        fun getInstance(context: Context): Database {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, Database::class.java, "database-trucks").build()
            }
            return INSTANCE!!
        }
    }

}
