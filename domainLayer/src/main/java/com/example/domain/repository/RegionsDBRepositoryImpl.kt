package com.example.domain.repository

import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.local.model.RegionRowEntity
import kotlinx.coroutines.flow.Flow

class RegionsDBRepositoryImpl(
    private val roomDataSource: RegionsRoomDataSource
) : RegionsDBRepository {

    override suspend fun checkRegionsDbData(): List<RegionRowEntity> {
        return roomDataSource.getRegionsData()
    }

    override fun getSearchedRegionsList(textField: String): Flow<List<RegionRowEntity>> {
        return roomDataSource.getSearchedRegionsList(textField)
    }

    override suspend fun getClosestRegion(longtitude: Double, latitude: Double): RegionRowEntity {
        return roomDataSource.getClosestRegion(longtitude, latitude)
    }


    override suspend fun insertRegions(regionRowEntity: RegionRowEntity) {
        roomDataSource.insertRegionData(regionRowEntity)
    }
}
