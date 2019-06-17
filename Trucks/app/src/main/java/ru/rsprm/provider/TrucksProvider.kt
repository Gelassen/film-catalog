package ru.rsprm.provider

import android.text.TextUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.rsprm.model.Truck
import ru.rsprm.repository.TrucksRepository
import ru.rsprm.storage.model.TruckEntity

class TrucksProvider(private val repository: TrucksRepository) {

    @Deprecated("Get all better suits app use cases")
    fun getTrucks(): Flowable<List<Truck>> {
        return Flowable.concat(getAllLocal(), getAll())
            .onErrorResumeNext(Flowable.empty())
    }

    fun getAll(): Flowable<List<Truck>> {
        return repository
            .getAll()
            .flatMapIterable { trucks -> trucks }
            .filter { truck -> !TextUtils.isEmpty(truck.name) }
            .toList()
            .toFlowable()
            .map<List<Truck>> { model ->
                val cacheEntity = ArrayList<TruckEntity>()
                for (entity in model) {
                    cacheEntity.add(TruckEntity(entity.id!!.toInt(), entity.name, entity.price, entity.comment))
                }
                repository.createLocal(cacheEntity)
                model
            }
//            .onErrorResumeNext(getAllLocal())
    }

    fun create(truck: Truck): Observable<Truck> {
        return repository.create(truck)
    }

    fun modify(truck: Truck): Observable<Truck> {
        return repository.modify(truck)
    }

    fun remove(id: Int): Observable<Long> {
        return repository
            .remove(id)
            .flatMap {  result ->
                if (result.equals(0)) {
                    repository
                        .removeLocal(id)
                        .flatMap { del ->
                            Observable.just(del.toLong())
                        }
                } else {
                    Observable.just(result)
                }
            }
    }

    fun getAllLocal(): Flowable<List<Truck>> {
        return repository
            .getAllLocal()
            .map<List<Truck>> { model ->
                val data = ArrayList<Truck>()
                for (entity in model) {
                    data.add(Truck(entity.id.toString(), entity.name, entity.price, entity.comment))
                }
                data
            }
    }
}
