package ru.rsprm.provider;

import android.text.TextUtils;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import ru.rsprm.model.Truck;
import ru.rsprm.repository.TrucksRepository;

import java.util.List;

@Deprecated
public class TruckProvider {

    private TrucksRepository repository;

    public TruckProvider(TrucksRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Truck>> getAll() {
        return new TrucksProvider(repository)
                .getAll()
                .flatMap(Flowable::fromIterable)
                .filter(new Predicate<Truck>() {
                    @Override
                    public boolean test(Truck truck) throws Exception {
                        return !TextUtils.isEmpty(truck.getName());
                    }
                })
                .toList()
                .toFlowable();
    }

    public Observable<Truck> create(Truck truck) {
        return repository.create(truck);
    }

    public Observable<Truck> modify(Truck truck) {
        return repository.modify(truck);
    }

    public Observable<Long> remove(Integer id) {
        return repository.remove(id);
    }
}
