package com.example.datalayer.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LanguageCodeEntity (
    @PrimaryKey val code: String,
    @ColumnInfo(name = "language") val language: String
)

@Entity(primaryKeys = ["source", "target"])
data class LanguageTargetEntity (
    val source: String,
    val target: String
)