package ru.rsprm.screens.add

import ru.rsprm.BaseContract
import ru.rsprm.model.Truck

interface IAddTruckContract {

    interface View: BaseContract.View  {

        fun onCreate(data: Truck)

        fun onNotValidName()

        fun onNotValidPrice()

        fun onNotValidComment()

        fun onAddTrackMode()

        fun onModifyTrackMode(truck: Truck)
    }

    interface Presenter {

        fun onStart(view: View, truck: Truck?)

        fun onSubmit(name: String?, price: String?, comment: String?)

        fun onSubmit(id: String?
                     , name: String?, price: String?, comment: String?)
    }
}
