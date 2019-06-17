package ru.rsprm.screens.list

import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.rsprm.model.Truck
import ru.rsprm.provider.TrucksProvider
import ru.rsprm.screens.BasePresenter

import javax.inject.Inject
import javax.inject.Named

class MainPresenter @Inject
constructor(
    private val provider: TrucksProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
) : BasePresenter<IListContract.View>(), IListContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun onStart(view: IListContract.View) {
        this.view = view
        getAll()
    }

    override fun onRemove(id: Int) {
        provider.remove(id)
            .subscribeOn(schedulerNetwork)
            .observeOn(schedulerUI)
            .subscribe(getRemoveTruckObserver(id))
    }

    override fun onStop() {
        disposable.dispose()
    }

    override fun onUpdate() {
        getAll()
    }

    private fun getAll() {
        disposable.add(provider.getAll()
            .subscribeOn(schedulerNetwork)
            .observeOn(schedulerUI)
            .doOnSubscribe { t -> view.showProgressIndicator() }
            .doOnNext { t -> view.hideProgressIndicator() }
            .doOnError { t -> view.hideProgressIndicator() }
            .subscribe(
                { model ->
                    view.onTrucks(model)
                    if (model.size == 0) {
                        view.showEmptyList()
                    } else {
                        view.hideEmptyList()
                    }
                },
                { e -> processError(e) })
        )
    }

    private fun getRemoveTruckObserver(id: Int): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                // no op
            }

            override fun onNext(data: Long?) {
                view.onRemoveTruck(id)
            }

            override fun onError(e: Throwable) {
                processError(e)
                view.onError(e)
            }

            override fun onComplete() {
                // no op
            }
        }
    }

}
