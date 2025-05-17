package com.zaed.ordertracker.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zaed.ordertracker.data.source.remote.model.FlightDto
import com.zaed.ordertracker.data.source.remote.model.mapper.toFlight
import com.zaed.ordertracker.data.source.remote.model.mapper.toFlightDto
import com.zaed.ordertracker.domain.model.Flight
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FlightRemoteDataSourceImpl(
    firestore: FirebaseFirestore,
) : FlightRemoteDataSource {
    private val flightCollection = firestore.collection("flights")

    override fun getAllFlights(): Flow<Result<List<Flight>>> =
        callbackFlow {
            try {
                flightCollection.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                    }
                    val flights = value?.toObjects<FlightDto>() ?: emptyList()
                    trySend(Result.success(flights.map(FlightDto::toFlight)))
                }
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose { }
        }

    override suspend fun addFlight(flight: Flight): Result<Unit> =
        try {
            val document = flightCollection.document()
            document.set(flight.copy(id = document.id).toFlightDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateFlight(flight: Flight): Result<Unit> =
        try {
            flightCollection.document(flight.id).set(flight.toFlightDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteFlight(id: String): Result<Unit> =
        try {
            flightCollection.document(id).delete()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
