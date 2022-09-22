package com.example.domain.usecase.map

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import com.example.util.ExcelReadHelper
import jxl.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckRegionsDbDataUseCase(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(applicationContext: Context) : List<Regions> {
        return with(regionsDBRepository.checkRegionsDbData()) {
            if (isEmpty()) {
                val regionsRowList = ExcelReadHelper.readExcel(applicationContext, "regions.xls")

                regionsRowList.map { cells ->
                    insertRegions(cells)
                    Regions(
                        cells[0].contents,
                        cells[1].contents,
                        cells[2].contents,
                        cells[3].contents,
                        cells[4].contents,
                        cells[5].contents,
                        cells[6].contents
                    )
                }
            } else {
                map {
                    Regions(
                        city = it.city,
                        gu = it.gu ?: "",
                        dong = it.dong ?: "",
                        nx = it.nx,
                        ny = it.ny,
                        latitude = it.latitude,
                        longtitude = it.longtitude
                    )
                }
            }
        }
    }

    private fun insertRegions(cell:Array<Cell>) = CoroutineScope(Dispatchers.IO).launch {
        regionsDBRepository.insertRegions(
            RegionRowEntity(
                cell[0].contents,
                cell[1].contents,
                cell[2].contents,
                cell[3].contents,
                cell[4].contents,
                cell[5].contents,
                cell[6].contents
            )
        )
    }

}