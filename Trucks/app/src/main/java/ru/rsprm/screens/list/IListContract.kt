package ru.rsprm.screens.list

import ru.rsprm.BaseContract
import ru.rsprm.model.Truck

interface IListContract {

    interface View: BaseContract.View {

        fun onTrucks(data: List<Truck>)

        fun onRemoveTruck(id: Int)

        fun showEmptyList()

        fun hideEmptyList()

        fun showProgressIndicator()

        fun hideProgressIndicator()
    }

    interface Presenter {

        fun onStart(view: View)

        fun onUpdate()


        fun onRemove(id: Int)

        fun onStop()
    }
}
