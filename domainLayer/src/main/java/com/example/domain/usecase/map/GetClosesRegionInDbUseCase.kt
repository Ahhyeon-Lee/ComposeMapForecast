package com.example.domain.usecase.map

import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository

class GetClosesRegionInDbUseCase(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(longtitude:Double, latitude:Double) : Regions {
        return with(regionsDBRepository.getClosestRegion(longtitude, latitude)) {
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
}