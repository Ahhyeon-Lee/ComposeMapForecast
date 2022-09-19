package com.example.datalayer.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class RegionRowEntity(
    @ColumnInfo(name = "city") val city:String,
    @ColumnInfo(name = "gu") val gu:String?,
    @ColumnInfo(name = "dong") val dong:String?,
    @ColumnInfo(name = "nx") val nx:String,
    @ColumnInfo(name = "ny") val ny:String,
    @ColumnInfo(name = "longtitude") val longtitude:String,
    @ColumnInfo(name = "latitude") val latitude:String
) {
    @PrimaryKey(autoGenerate = true) val id:Long = 0
}

data class Regions(
    val city:String,
    val gu:String,
    val dong:String,
    val nx:String,
    val ny:String,
    val longtitude:String,
    val latitude:String
)