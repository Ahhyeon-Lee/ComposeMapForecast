package com.example.domain.usecase.translate

import android.content.Context
import com.example.domain.model.LanguageTargetData
import com.example.domain.repository.translate.LanguageRepository
import com.example.domain.util.ExcelReadHelper
import javax.inject.Inject

class GetLanguageTargetUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend fun invoke(applicationContext: Context) : List<LanguageTargetData> =
        with(repository.getLanguageTarget()) {
            ifEmpty {
                val languageTargetExcelList = ExcelReadHelper.readExcel(applicationContext, "translate.xls", 1)
                val languageTargetData = languageTargetExcelList.map { cell ->
                    LanguageTargetData(cell[0].contents, cell[1].contents)
                }
                repository.insertLanguageTarget(languageTargetData)
                repository.getLanguageTarget()
            }
        }
}