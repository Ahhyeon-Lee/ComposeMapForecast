package com.example.domain.usecase.map

import android.graphics.Region
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearchedRegionsUseCase(
    private val regionsDBRepository: RegionsDBRepository
) {
    fun invoke(textField:String) : Flow<List<Regions>> {
        return regionsDBRepository.getSearchedRegionsList(textField)
            .map {
            it.map {
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

    suspend fun test(longtitude:Double, latitude:Double) : Regions {
        return with(regionsDBRepository.getClosestRegion(longtitude, latitude)) {
            Regions(
                city = city,
                gu = gu ?: "",
                dong = dong ?: "",
                nx = nx,
                ny = ny,
                latitude = latitude,
                longtitude = longtitude
            )
        }
    }
}