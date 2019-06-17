package ru.rsprm.screens.add

import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.rsprm.model.Truck
import ru.rsprm.provider.TrucksProvider
import ru.rsprm.provider.ValidatorProvider
import ru.rsprm.screens.BasePresenter
import javax.inject.Inject
import javax.inject.Named

class AddTruckPresenter @Inject
constructor(
    @Named("NETWORK") val schedulerNetwork: Scheduler,
    @Named("UI") val schedulerUI: Scheduler,
    private val trucksProvider: TrucksProvider,
    private val validatorProvider: ValidatorProvider)
    : BasePresenter<IAddTruckContract.View>(), IAddTruckContract.Presenter {

    private var mode: Int = 0

    override fun onStart(view: IAddTruckContract.View, truck: Truck?) {
        this.view = view
        if (truck == null) {
            mode = MODE_ADD_TRUCK
            view.onAddTrackMode()
        } else {
            mode = MODE_MODIFY_TRUCK
            view.onModifyTrackMode(truck!!)
        }
    }

    override fun onSubmit(name: String?, price: String?, comment: String?) {
        // TODO make id dynamic
        onSubmit(null, name, price, comment)
    }

    override fun onSubmit(id: String?, name: String?, price: String?, comment: String?) {
        if (!validatorProvider.isValidTextField(name!!)) {
            view.onNotValidName()
        } else if (!validatorProvider.isValidNumericField(price!!)) {
            view.onNotValidPrice()
        } else if (!validatorProvider.isValidLength(comment)) {
            view.onNotValidComment()
        } else {
            val truck = Truck(id, name, price, comment)
            submit(truck)
        }
    }

    private fun submit(truck : Truck?) {
        when (mode) {
            MODE_ADD_TRUCK -> {
                trucksProvider.create(truck!!)
                    .subscribeOn(schedulerNetwork)
                    .observeOn(schedulerUI)
                    .subscribe(truckObserver)
            }
            MODE_MODIFY_TRUCK -> {
                trucksProvider.modify(truck!!)
                    .subscribeOn(schedulerNetwork)
                    .observeOn(schedulerUI)
                    .subscribe(truckObserver)
            }
        }
    }

    val truckObserver: Observer<Truck>
        get() = object : Observer<Truck> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(data: Truck) {
                view.onCreate(data)
                // do not put it into the cache, the right way is request again on the main screen
            }

            override fun onError(error: Throwable) {
                processError(error)
            }

            override fun onComplete() {}
        }

    companion object {

        private val MODE_ADD_TRUCK = 0

        private val MODE_MODIFY_TRUCK = 1
    }
}
