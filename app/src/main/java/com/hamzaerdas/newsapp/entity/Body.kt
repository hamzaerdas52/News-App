package com.hamzaerdas.newsapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Body(
    val p: String? = "",
    val h3: String? = "",
) : Parcelable