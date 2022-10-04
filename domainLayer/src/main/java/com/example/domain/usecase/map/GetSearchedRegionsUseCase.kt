package com.example.domain.usecase.map

import com.example.domain.model.Regions
import com.example.domain.repository.map.RegionsDBRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchedRegionsUseCase @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
) {
    fun invoke(textField:String) : Flow<List<Regions>> {
        return regionsDBRepository.getSearchedRegionsList(textField)
    }
}