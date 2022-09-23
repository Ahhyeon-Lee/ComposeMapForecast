package com.example.datalayer.local.datasource

import com.example.datalayer.local.dao.TranslateRoomDao
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity

class TranslateRoomDataSource(
    private val translateDao: TranslateRoomDao
) {
    suspend fun getLanguageCode() : List<LanguageCodeEntity> = translateDao.getLanguageAll()
    suspend fun getLanguageTarget() : List<LanguageTargetEntity> = translateDao.getTargetAll()
    suspend fun insertLanguageCode(items: List<LanguageCodeEntity>) = translateDao.insertLanguageCodeAll(*items.toTypedArray())
    suspend fun insertLanguageTarget(items: List<LanguageTargetEntity>) = translateDao.insertLanguageTargetAll(*items.toTypedArray())
}