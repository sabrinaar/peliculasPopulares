package com.sabrina.peliculaspopulares.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pelicula (
    var portada: String = "",
    var titulo: String = "",
): Parcelable
