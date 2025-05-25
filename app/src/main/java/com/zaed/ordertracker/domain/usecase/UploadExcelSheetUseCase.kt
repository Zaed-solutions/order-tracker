package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.FileExportRepository
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import java.io.File

class UploadExcelSheetUseCase(
    private val fileExportRepository: FileExportRepository,
    private val shipmentRepository: ShipmentRepository,
) {
    suspend operator fun invoke(
        flightId: String,
        file: File,
    ): Result<String> =
        fileExportRepository.uploadExcelToFolder(file).also {
            it.onSuccess {
                shipmentRepository.updateFlightShipmentsExportedStatus(flightId)
            }
        }
}
