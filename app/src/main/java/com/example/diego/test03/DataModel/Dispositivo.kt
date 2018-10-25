package com.example.diego.test03.DataModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Dispositivo(val uid: String, val nombre: String): Parcelable {
    constructor() : this("", "")
}