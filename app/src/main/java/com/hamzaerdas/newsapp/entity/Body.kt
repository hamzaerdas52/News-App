package com.hamzaerdas.newsapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Body(
    val p: String? = "",
    val h3: String? = "",
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}