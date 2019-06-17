package ru.rsprm.storage.dao

import androidx.room.*
import io.reactivex.Flowable
import ru.rsprm.storage.model.TruckEntity

@Dao
interface TruckDao {

    @Query("SELECT * FROM trucks")
    fun getAll(): Flowable<List<TruckEntity>>

    @Query("DELETE FROM trucks WHERE id = :id")
    fun delete(id: Int): Int

    @Update
    fun update(entity: TruckEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TruckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<TruckEntity>)
}
