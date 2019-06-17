package ru.rsprm.repository

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.rsprm.model.Truck
import ru.rsprm.network.Api
import ru.rsprm.storage.Database
import ru.rsprm.storage.dao.TruckDao
import ru.rsprm.storage.model.TruckEntity

class TrucksRepository(
    private val context: Context,
    private val database: Database,
    private val api: Api) {

    private val local: TruckDao

    init {
        local = database.truckDao
    }

    fun getAll(): Flowable<List<Truck>> {
        return api.trucks
    }

    fun create(truck: Truck): Observable<Truck> {
        return api.create(truck)
    }

    fun modify(truck: Truck): Observable<Truck> {
        return api.edit(truck, truck.id!!.toInt())
    }

    fun remove(id: Int): Observable<Long> {
        return api.remove(id)
    }

    fun getAllLocal(): Flowable<List<TruckEntity>> {
        return local.getAll()
    }

    fun createLocal(entity: TruckEntity) {
        local.insert(entity)
    }

    fun createLocal(entities: List<TruckEntity>) {
        local.insert(entities)
    }

    fun modifyLocal(entity: TruckEntity): Observable<Int> {
        return Observable.just(local!!.update(entity))
    }

    fun removeLocal(id: Int): Observable<Int> {
        return Observable.just((local!!.delete(id)))
    }
}
