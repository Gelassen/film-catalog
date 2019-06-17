package ru.rsprm.provider

import android.text.TextUtils

class ValidatorProvider {

    fun isValidLength(data: String?) : Boolean {
        return data == null || data.length <= MAX_SYMBOLS
    }

    fun isValidTextField(data : String) : Boolean {
        return !TextUtils.isEmpty(data)
                && data.length <= MAX_SYMBOLS
    }

    fun isValidNumericField(data : String) : Boolean {
        return !TextUtils.isEmpty(data)
                && TextUtils.isDigitsOnly(data)
                && data.length <= MAX_SYMBOLS_PRICE
    }

    companion object {

        private val MAX_SYMBOLS = 360

        private val MAX_SYMBOLS_PRICE = 7

    }

}
