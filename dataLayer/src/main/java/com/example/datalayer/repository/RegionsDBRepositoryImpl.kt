package com.example.datalayer.repository

import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import jxl.Cell
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegionsDBRepositoryImpl @Inject constructor(
    private val roomDataSource: RegionsRoomDataSource
) : RegionsDBRepository {

    override suspend fun checkRegionsDbData(): List<Regions> {
        return roomDataSource.getRegionsData().map {
            Regions(
                city = it.city,
                gu = it.gu ?: "",
                dong = it.dong ?: "",
                address = it.address ?: "",
                nx = it.nx,
                ny = it.ny,
                latitude = it.latitude,
                longtitude = it.longtitude
            )
        }
    }

    override fun getSearchedRegionsList(textField: String): Flow<List<Regions>> {
        return roomDataSource.getSearchedRegionsList(textField).map {
            it.map {
                Regions(
                    city = it.city,
                    gu = it.gu ?: "",
                    dong = it.dong ?: "",
                    address = it.address ?: "",
                    nx = it.nx,
                    ny = it.ny,
                    latitude = it.latitude,
                    longtitude = it.longtitude
                )
            }
        }
    }

    override suspend fun getClosestRegion(longtitude: Double, latitude: Double): Regions {
        return with (roomDataSource.getClosestRegion(longtitude, latitude)) {
            Regions(
                city = city,
                gu = gu ?: "",
                dong = dong ?: "",
                address = address ?: "",
                nx = nx,
                ny = ny,
                latitude = latitude,
                longtitude = longtitude
            )
        }
    }

    override suspend fun insertRegions(cell: Array<Cell>) {
        roomDataSource.insertRegionData(
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
