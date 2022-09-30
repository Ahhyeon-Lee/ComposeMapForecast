package com.example.datalayer.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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

@Entity
data class TranslateHistoryEntity(
    @ColumnInfo(name = "sourceCode") val sourceCode: String,
    @ColumnInfo(name = "sourceText") val sourceText: String,
    @ColumnInfo(name = "targetCode") val targetCode: String,
    @ColumnInfo(name = "targetText") val targetText: String,
    @ColumnInfo(name = "insertDate") val insertDate: Long
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}

data class TranslateHistoryListEntity(
    val sourceCode: String = "",
    val sourceText: String = "",
    val targetCode: String = "",
    val targetText: String = "",
    val sourceLanguage: String = "",
    val targetLanguage: String = ""
)