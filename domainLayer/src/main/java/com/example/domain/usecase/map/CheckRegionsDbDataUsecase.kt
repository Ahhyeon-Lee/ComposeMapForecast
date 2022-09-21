package com.example.domain.usecase.map

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import com.example.util.ExcelReadHelper
import jxl.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CheckRegionsDbDataUsecase(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(applicationContext: Context, callBack:(List<Regions>) -> Unit) {
        regionsDBRepository.checkRegionsDbData()
            .onEach {
                if (it.isEmpty()) {
                    val regionsRowList = ExcelReadHelper.readExcel(applicationContext, "regions.xls")
                    val list = regionsRowList.map { cells ->
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
                    callBack.invoke(list)
                } else {
                    val list = it.map {
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
                    callBack.invoke(list)
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Default))
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