package com.example.domain.usecase.translate

import android.content.Context
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.domain.model.LanguageTargetData
import com.example.domain.repository.translate.LanguageRepository
import com.example.util.ExcelReadHelper

class GetLanguageTargetUseCase(
    private val repository: LanguageRepository
) {
    suspend fun invoke(applicationContext: Context) : List<LanguageTargetData> =
        with(repository.getLanguageTarget()) {
            ifEmpty {
                val languageTargetExcelList = ExcelReadHelper.readExcel(applicationContext, "translate.xls", 1)
                val languageTargetData = languageTargetExcelList.map { cell ->
                    LanguageTargetEntity(cell[0].contents, cell[1].contents)
                }
                repository.insertLanguageTarget(languageTargetData)
                repository.getLanguageTarget()
            }
        }
}