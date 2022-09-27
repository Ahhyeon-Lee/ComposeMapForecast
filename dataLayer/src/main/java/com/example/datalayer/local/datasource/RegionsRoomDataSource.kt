package com.example.datalayer.local.datasource

import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.dao.RegionsRoomDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RegionsRoomDataSource(
    private val regionDao : RegionsRoomDao
) {

    suspend fun getRegionsData() : List<RegionRowEntity> {
        return regionDao.getAll()
    }

    fun getSearchedRegionsList(textField:String) : Flow<List<RegionRowEntity>> {
        return regionDao.getSearchedRegionsList(textField)
    }

    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : RegionRowEntity {
        return regionDao.getClosestRegion(longtitude, latitude)
    }

    suspend fun insertRegionData(regionRowEntity: RegionRowEntity) {
        withContext(Dispatchers.IO) {
            regionDao.insert(regionRowEntity)
        }
    }
}