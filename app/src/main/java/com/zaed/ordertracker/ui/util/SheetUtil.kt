package com.zaed.ordertracker.ui.util

import android.content.Context
import android.os.Environment
import android.util.Log
import com.zaed.ordertracker.domain.model.Shipment
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

data class ShipmentRow(
    val count: String,
    val employeeName: String,
    val shipmentDate: String,
    val shipmentTime: String,
    val shipmentType: String,
    val shipmentNumber: String,
    val shipmentQuantity: String,
    val shipmentWeight: String,
    val masterPackageWeight: String,
    val masterPackage: String,
)

fun List<Shipment>.toRows(): List<ShipmentRow> =
    this.mapIndexed { index, shipment ->
        ShipmentRow(
            count = (index + 1).toString(),
            employeeName = shipment.addedByName,
            shipmentDate = shipment.addedAt.formatDate(),
            shipmentTime = shipment.addedAt.formatTime(),
            shipmentType = shipment.type.name,
            shipmentNumber = shipment.shipmentNumber,
            shipmentQuantity = shipment.quantity.toString(),
            shipmentWeight = shipment.weight.toString(),
            masterPackageWeight = shipment.masterPackageWeight.let { if (shipment.masterPackageName.isBlank()) "" else it.toString() },
            masterPackage = shipment.masterPackageName,
        )
    }

fun List<ShipmentRow>.exportShipmentsAsExcel(
    context: Context,
    flightNumber: String,
    headers: List<String>,
): File? {
    try {
        val fileName = "$flightNumber.xlsx"
        val workbook = XSSFWorkbook()

        // Create a single sheet with all shipment records
        createSheet(workbook, this, headers)

        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        FileOutputStream(file).use { outputStream ->
            workbook.write(outputStream)
        }
        workbook.close()
        return file
    } catch (e: Exception) {
        Log.e("SheetUtil", "exportRecordsAsExcel: ${e.message}")
        e.printStackTrace()
        return null
    }
}

private fun createSheet(
    workbook: XSSFWorkbook,
    records: List<ShipmentRow>,
    headers: List<String>,
) {
    val sheet = workbook.createSheet()

    // Set a reasonable default column width for all columns
    headers.indices.forEach { sheet.setColumnWidth(it, 15 * 256) } // 15 characters width

    // Create header row
    val headerRow = sheet.createRow(0)
    headers.forEachIndexed { index, header ->
        val cell = headerRow.createCell(index)
        cell.setCellValue(header.uppercase())
    }

    // Fill data rows with shipment data
    records.forEachIndexed { index, record ->
        val row: Row = sheet.createRow(index + 1)
        var columnIndex = 0

        // Add shipment fields based on ShipmentRow data class
        row.createCell(columnIndex++).setCellValue(record.count)
        row.createCell(columnIndex++).setCellValue(record.employeeName.trim())
        row.createCell(columnIndex++).setCellValue(record.shipmentDate)
        row.createCell(columnIndex++).setCellValue("\t")
        row.createCell(columnIndex++).setCellValue(record.shipmentTime)
        row.createCell(columnIndex++).setCellValue(record.shipmentType)
        row.createCell(columnIndex++).setCellValue(record.shipmentNumber.trim())
        row.createCell(columnIndex++).setCellValue(record.shipmentQuantity)
        row.createCell(columnIndex++).setCellValue(record.shipmentWeight)
        row.createCell(columnIndex++).setCellValue(record.masterPackageWeight)
        row.createCell(columnIndex).setCellValue(record.masterPackage.trim())
    }
}
