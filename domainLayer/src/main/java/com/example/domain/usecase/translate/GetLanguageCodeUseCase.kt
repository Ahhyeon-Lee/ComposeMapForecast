package com.example.domain.usecase.translate

import android.content.Context
import com.example.domain.model.LanguageCodeData
import com.example.domain.repository.translate.LanguageRepository
import com.example.util.ExcelReadHelper
import javax.inject.Inject

class GetLanguageCodeUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend fun invoke(applicationContext: Context) : List<LanguageCodeData> =
        with(repository.getLanguageCode()) {
            ifEmpty {
                val languageCodeExcelList = ExcelReadHelper.readExcel(applicationContext, "translate.xls", 0)
                val languageCodeData = languageCodeExcelList.map { cell ->
                    LanguageCodeData(cell[0].contents, cell[1].contents)
                }
                repository.insertLanguageCode(languageCodeData)
                repository.getLanguageCode()
            }
        }
}