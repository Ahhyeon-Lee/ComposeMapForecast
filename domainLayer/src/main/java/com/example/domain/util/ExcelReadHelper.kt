package com.example.domain.util

import android.content.Context
import jxl.Cell
import jxl.Workbook
import java.io.IOException

object ExcelReadHelper {

    fun readExcel(_context: Context, _assetFileName: String, sheetNumber: Int = 0): ArrayList<Array<Cell>> {
        val cellList = arrayListOf<Array<Cell>>()
        try {
            val inputStream = _context.resources.assets.open(_assetFileName)

            val wb = Workbook.getWorkbook(inputStream)
            val sheet = wb.getSheet(sheetNumber)
            val colTotal = sheet.columns
            val rowIndexStart = 1
            val rowTotal = sheet.getColumn(colTotal - 1).size

            for (row in rowIndexStart until rowTotal) {
                cellList.add(sheet.getRow(row))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return cellList
    }
}