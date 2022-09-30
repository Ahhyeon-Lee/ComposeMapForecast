package com.example.datalayer.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class RegionRowEntity(
    @ColumnInfo(name = "city") var city:String,
    @ColumnInfo(name = "gu") var gu:String?,
    @ColumnInfo(name = "dong") var dong:String?,
    @ColumnInfo(name = "address") var address:String?,
    @ColumnInfo(name = "nx") var nx:String,
    @ColumnInfo(name = "ny") var ny:String,
    @ColumnInfo(name = "longtitude") var longtitude:Double,
    @ColumnInfo(name = "latitude") var latitude:Double
) {
    @PrimaryKey(autoGenerate = true) var id:Long = 0
}

data class WeatherSearchHistoryEntity(
    @ColumnInfo(name = "city") var city:RegionRowEntity,
    @ColumnInfo(name = "gu") var gu:String?,
    @ColumnInfo(name = "dong") var dong:String?,
    @ColumnInfo(name = "address") var address:String?,
    @ColumnInfo(name = "nx") var nx:String,
    @ColumnInfo(name = "ny") var ny:String,
    @ColumnInfo(name = "longtitude") var longtitude:Double,
    @ColumnInfo(name = "latitude") var latitude:Double
) {
    @PrimaryKey(autoGenerate = true) var id:Long = 0
}