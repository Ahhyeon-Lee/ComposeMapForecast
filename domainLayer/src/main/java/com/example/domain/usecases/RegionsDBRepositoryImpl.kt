package com.example.domain.usecases

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.repository.RegionsDBRepository
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.domain.usecases.model.Regions
import com.example.util.ExcelReadHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RegionsDBRepositoryImpl(
    private val roomDataSource: RegionsRoomDataSource
) : RegionsDBRepository {

    override fun checkRegionsDbData(applicationContext: Context) {
        roomDataSource.getRegionsData()
            .onEach {
                if (it.isEmpty()) {
                    val regionsRowList = ExcelReadHelper.readExcel(applicationContext, "regions.xls")
                    regionsRowList.forEach {
                        roomDataSource.insertRegionData(
                            RegionRowEntity(
                                it[0].contents,
                                it[1].contents,
                                it[2].contents,
                                it[3].contents,
                                it[4].contents,
                                it[5].contents,
                                it[6].contents
                            )
                        ) }
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
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Default))
    }
}
