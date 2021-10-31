package com.sabrina.peliculaspopulares.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRating (
        val status_code: Int,
        val status_message: String
    ): Parcelable

@Parcelize
data class ResponseSession(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
):Parcelable