package com.example.domain.repository

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.domain.model.Regions
import com.example.util.ExcelReadHelper
import jxl.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegionsDBRepositoryImpl(
    private val roomDataSource: RegionsRoomDataSource
) : RegionsDBRepository {

    override suspend fun checkRegionsDbData(): List<RegionRowEntity> {
        return roomDataSource.getRegionsData()
    }

    override suspend fun insertRegions(regionRowEntity: RegionRowEntity) {
        roomDataSource.insertRegionData(regionRowEntity)
    }
}
