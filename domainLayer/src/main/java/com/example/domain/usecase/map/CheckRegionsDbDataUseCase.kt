package com.example.domain.usecase.map

import android.content.Context
import com.example.domain.repository.map.RegionsDBRepository
import com.example.util.ExcelReadHelper
import javax.inject.Inject

class CheckRegionsDbDataUseCase @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(applicationContext: Context) {
        with(regionsDBRepository.checkRegionsDbData()) {
            if (isEmpty()) {
                val regionsRowList = ExcelReadHelper.readExcel(applicationContext, "regions.xls")
                regionsRowList.map { cell ->
                    regionsDBRepository.insertRegions(cell)
                }
            }
        }
    }

}