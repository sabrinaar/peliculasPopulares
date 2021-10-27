package com.sabrina.peliculaspopulares.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponsePeliculasPopulares (
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    var popularPelisList: MutableList<Pelicula>,
    @SerializedName("total_pages")
    var totalPaginas: Int,
    @SerializedName("total_results")
    var totalResultados: Int
): Parcelable
