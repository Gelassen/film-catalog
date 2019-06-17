package ru.rsprm

interface BaseContract {

    interface View {

        fun onError(error: String)

        fun onError(error: Throwable)

    }
}
