package com.sabrina.peliculaspopulares.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pelicula (
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    var portada: String = "",
    @SerializedName("original_title")
    var titulo: String = "",
): Parcelable

