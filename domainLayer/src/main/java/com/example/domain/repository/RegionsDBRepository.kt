package com.example.domain.repository

import android.content.Context
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.model.Regions
import jxl.Cell
import kotlinx.coroutines.flow.Flow

interface RegionsDBRepository {

    suspend fun checkRegionsDbData() : Flow<List<RegionRowEntity>>

    suspend fun insertRegions(regionRowEntity: RegionRowEntity)

}