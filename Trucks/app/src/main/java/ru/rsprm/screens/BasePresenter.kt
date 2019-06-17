package ru.rsprm.screens

import retrofit2.HttpException
import ru.rsprm.BaseContract
import ru.rsprm.exceptions.NoConnectivityException

import java.io.IOException

open class BasePresenter<T : BaseContract.View> {

    protected lateinit var view: T

    protected fun processError(error: Throwable) {
        try {
            // We had non-200 http error
            if (error is HttpException) {
                view.onError("Сервер недоступен")
            } else if (error is NoConnectivityException) {
                view.onError("Нет соединения с интернетом. Проверьте настройки сети")
            } else if (error is IOException) {
                view.onError("Что-то пошло не так. Попробуйте позже")
            }
        } catch (e: Exception) {
            view.onError(e)
        }
    }

}
