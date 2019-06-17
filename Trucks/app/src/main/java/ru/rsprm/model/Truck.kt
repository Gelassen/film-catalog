package ru.rsprm.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Truck: Serializable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("nameTruck")
    var name: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("comment")
    var comment: String? = null

    constructor() {}

    constructor(id: String?, name: String, price: String, comment: String?) {
        this.id = id
        this.name = name
        this.price = price
        this.comment = comment
    }
}
