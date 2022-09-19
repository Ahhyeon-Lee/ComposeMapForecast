package com.example.datalayer.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class RegionRowEntity(
    @ColumnInfo(name = "city") var city:String,
    @ColumnInfo(name = "gu") var gu:String?,
    @ColumnInfo(name = "dong") var dong:String?,
    @ColumnInfo(name = "nx") var nx:String,
    @ColumnInfo(name = "ny") var ny:String,
    @ColumnInfo(name = "longtitude") var longtitude:String,
    @ColumnInfo(name = "latitude") var latitude:String
) {
    @PrimaryKey(autoGenerate = true) var id:Long = 0
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