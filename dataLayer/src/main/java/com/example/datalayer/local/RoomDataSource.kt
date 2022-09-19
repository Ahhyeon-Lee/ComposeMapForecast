package com.example.datalayer.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RoomDataSource(
    private val regionDao : RoomDao
) {

    suspend fun getRegionsData() : Flow<List<Regions>> {
        return flowOf(
            regionDao.getAll().map {
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
        )
    }
}