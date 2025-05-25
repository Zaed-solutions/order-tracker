package com.zaed.ordertracker.data.source.remote

import android.util.Log
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.data.source.remote.model.ShipmentDto
import com.zaed.ordertracker.data.source.remote.model.mapper.toShipment
import com.zaed.ordertracker.data.source.remote.model.mapper.toShipmentDto
import com.zaed.ordertracker.domain.model.Shipment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ShipmentRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : ShipmentRemoteDataSource {
    private val shipmentCollection = firestore.collection("shipments")

    override fun getFlightShipments(flightId: String): Flow<Result<List<Shipment>>> =
        callbackFlow {
            try {
                shipmentCollection
                    .whereEqualTo(ShipmentDto::flightId.name, flightId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(Result.failure(error))
                        }
                        val shipments = snapshot?.toObjects(ShipmentDto::class.java) ?: emptyList()
                        trySend(Result.success(shipments.map(ShipmentDto::toShipment)))
                    }
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose { }
        }

    override suspend fun createShipment(shipment: Shipment): Result<Unit> =
        try {
            Log.d("ShipmentRemoteDataSource", "Creating shipment: $shipment")
            shipmentCollection.document().let { docRef ->
                docRef.set(shipment.toShipmentDto().copy(id = docRef.id))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateShipment(updatedShipment: Shipment): Result<Unit> =
        try {
            shipmentCollection
                .document(updatedShipment.id)
                .set(updatedShipment.toShipmentDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteShipment(shipmentId: String): Result<Unit> =
        try {
            shipmentCollection.document(shipmentId).delete()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getShipmentsByMasterPackageId(masterPackageId: String): Flow<Result<List<Shipment>>> =
        callbackFlow {
            try {
                shipmentCollection
                    .whereEqualTo(ShipmentDto::masterPackageId.name, masterPackageId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(Result.failure(error))
                        }
                        val shipments = snapshot?.toObjects(ShipmentDto::class.java) ?: emptyList()
                        trySend(Result.success(shipments.map(ShipmentDto::toShipment)))
                    }
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose {}
        }

    override suspend fun doesMasterPackageHaveUnExportedShipments(id: String): Result<Boolean> =
        try {
            val querySnapshot =
                shipmentCollection
                    .where(
                        Filter.and(
                            Filter.equalTo(ShipmentDto::masterPackageId.name, id),
                            Filter.equalTo(ShipmentDto::exported.name, false),
                        ),
                    ).get()
                    .await()
            Result.success(querySnapshot.documents.isNotEmpty())
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun doesFlightHaveUnExportedShipments(id: String): Result<Boolean> =
        try {
            val querySnapshot =
                shipmentCollection
                    .where(
                        Filter.and(
                            Filter.equalTo(ShipmentDto::flightId.name, id),
                            Filter.equalTo(ShipmentDto::exported.name, false),
                        ),
                    ).get()
                    .await()
            Result.success(querySnapshot.documents.isNotEmpty())
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateFlightShipmentsExportedStatus(flightId: String): Result<Unit> =
        try {
            val batch = firestore.batch()
            val querySnapshot =
                shipmentCollection.whereEqualTo(ShipmentDto::flightId.name, flightId).get().await()
            querySnapshot.documents.forEach {
                batch.update(it.reference, ShipmentDto::exported.name, true)
            }
            batch.commit()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
