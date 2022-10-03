package com.example.domain.usecase.map

import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import javax.inject.Inject

class GetClosestRegionInDbUseCase @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(longtitude:Double, latitude:Double) : Regions {
        return regionsDBRepository.getClosestRegion(longtitude, latitude)
    }
}