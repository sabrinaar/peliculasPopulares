package com.sabrina.peliculaspopulares.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponsePeliculasPopulares (
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val popularPelisList: MutableList<Pelicula>,
    @SerializedName("total_pages")
    val totalPaginas: Int,
    @SerializedName("total_results")
    val totalResultados: Int
): Parcelable
