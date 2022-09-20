package com.example.datalayer.local.datasource

import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.dao.RegionsRoomDao
import kotlinx.coroutines.flow.Flow

class RegionsRoomDataSource(
    private val regionDao : RegionsRoomDao
) {

    fun getRegionsData() : Flow<List<RegionRowEntity>> {
        return regionDao.getAll()
    }

    suspend fun insertRegionData(regionRowEntity: RegionRowEntity) {
        regionDao.insert(regionRowEntity)
    }
}