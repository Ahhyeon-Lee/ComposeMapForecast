package com.example.domain.repository

import com.example.domain.model.Regions
import jxl.Cell
import kotlinx.coroutines.flow.Flow

interface RegionsDBRepository {

    suspend fun checkRegionsDbData() : List<Regions>

    fun getSearchedRegionsList(textField:String) : Flow<List<Regions>>

    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : Regions

    suspend fun insertRegions(cell: Array<Cell>)

}