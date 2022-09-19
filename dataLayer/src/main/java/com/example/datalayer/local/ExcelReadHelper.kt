package com.example.datalayer.local

import android.content.Context
import android.util.Log
import jxl.Workbook
import java.io.IOException
import java.util.stream.Stream
import kotlin.streams.toList

object ExcelReadHelper {

    fun readExcel(_context: Context, _assetFileName:String) {
        try {
            val inputStream = _context.resources.assets.open(_assetFileName)

            val wb = Workbook.getWorkbook(inputStream)
            val sheet = wb.getSheet(0)
            val colTotal = sheet.columns
            val rowIndexStart = 1
            val rowTotal = sheet.getColumn(colTotal - 1).size

            for (row in rowIndexStart until rowTotal) {
                // col : 컬럼 순서, contents : 데이터 값
                for (col in 0 until colTotal) {
                    val contents = sheet.getCell(col, row).contents
                    Log.i("아현", "row : $row / col : $col")
                    if (row > 0) {
                        Log.i("아현", "$col 번째 : $contents")
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

//    private fun rowCellsToDataClass(stream: Stream<Cell>) : CellValues {
//        val cells = stream.toList()
//        return CellValues(cells[0].value.toString(), cells[1].value.toString(), cells[2].value.toString(), cells[3].value.toString(), cells[4].value.toString(), cells[5].value.toString(), cells[6].value.toString())
//    }
}