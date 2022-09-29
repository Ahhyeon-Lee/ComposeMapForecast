package com.example.domain.usecase.map

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import com.example.util.ExcelReadHelper
import jxl.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CheckRegionsDbDataUseCase(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(applicationContext: Context) {
        with(regionsDBRepository.checkRegionsDbData()) {
            if (isEmpty()) {
                val regionsRowList = ExcelReadHelper.readExcel(applicationContext, "regions.xls")
                regionsRowList.map { cell ->
                    regionsDBRepository.insertRegions(
                        RegionRowEntity(
                            cell[0].contents,
                            cell[1].contents,
                            cell[2].contents,
                            "${cell[0].contents} ${cell[1].contents} ${cell[2].contents}",
                            cell[3].contents,
                            cell[4].contents,
                            cell[5].contents.toDoubleOrNull() ?: 0.0,
                            cell[6].contents.toDoubleOrNull() ?: 0.0
                        )
                    )
                }
            }
        }
    }

}