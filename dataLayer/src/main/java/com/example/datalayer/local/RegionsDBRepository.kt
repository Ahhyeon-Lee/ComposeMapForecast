package com.example.datalayer.local

import android.content.Context
import com.example.util.ResultUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegionsDBRepository(
    private val roomDataSource: RoomDataSource
) {

    private val _dbState : MutableStateFlow<ResultUiState<List<Regions>>> = MutableStateFlow(ResultUiState.UnInitialize)
    val dbState = _dbState.asStateFlow()

    fun checkRegionsDbData(applicationContext: Context) = CoroutineScope(Dispatchers.Default).launch {
        roomDataSource.getRegionsData()
            .onStart {
                _dbState.value = ResultUiState.Loading
            }
            .onEach {
                if (it.isEmpty()) {
                    ExcelReadHelper.readExcel(applicationContext, "regions.xls")
                } else {
                    _dbState.value = ResultUiState.Success(it)
                }
            }
            .onCompletion {
                _dbState.value = ResultUiState.Finish
            }
            .launchIn(CoroutineScope(Dispatchers.Default))

    }

}