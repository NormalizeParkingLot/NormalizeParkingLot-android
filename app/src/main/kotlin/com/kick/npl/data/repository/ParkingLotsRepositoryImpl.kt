package com.kick.npl.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kick.npl.data.model.ParkingLotEntity
import com.kick.npl.data.model.toParkingLotData
import com.kick.npl.data.util.module
import com.kick.npl.model.ParkingLotData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ParkingLotsRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : ParkingLotsRepository {

    override suspend fun setParkingLot(parkingLot: ParkingLotEntity) {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(parkingLot.id)
                .set(parkingLot)
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun getParkingLot(id: String): ParkingLotEntity? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (!document.exists()) {
                        cancellableContinuation.resume(null)
                        return@addOnSuccessListener
                    }

                    document.toObject(ParkingLotEntity::class.java).also { entity ->
                        cancellableContinuation.resume(entity)
                    }
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun getAllParkingLots(): List<ParkingLotData>? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .get()
                .addOnSuccessListener { documents ->
                    documents
                        .asSequence()
                        .map { it.toObject(ParkingLotEntity::class.java) }
                        .map { it.toParkingLotData() }
                        .toList()
                        .let { cancellableContinuation.resume(it) }

                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }

        }
    }
}