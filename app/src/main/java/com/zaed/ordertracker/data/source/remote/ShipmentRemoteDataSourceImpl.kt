package com.zaed.ordertracker.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.data.source.remote.model.ShipmentDto
import com.zaed.ordertracker.data.source.remote.model.mapper.toShipment
import com.zaed.ordertracker.data.source.remote.model.mapper.toShipmentDto
import com.zaed.ordertracker.domain.model.Shipment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ShipmentRemoteDataSourceImpl(
    firestore: FirebaseFirestore,
) : ShipmentRemoteDataSource {
    private val shipmentCollection = firestore.collection("shipments")

    override fun getAllShipments(): Flow<Result<List<Shipment>>> =
        callbackFlow {
            try {
                shipmentCollection.addSnapshotListener { snapshot, error ->
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
            shipmentCollection.document().let { docRef ->
                docRef.set(shipment.toShipmentDto().copy(id = docRef.id))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateShipment(updatedShipment: Shipment): Result<Unit> {
        return try{
            shipmentCollection
                .document(updatedShipment.id)
                .set(updatedShipment.toShipmentDto())
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteShipment(shipmentId: String): Result<Unit> {
        return try {
            shipmentCollection.document(shipmentId).delete()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
