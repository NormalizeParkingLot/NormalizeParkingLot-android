package com.kick.npl.ui.parkinglot

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.local.UserIdDataSource
import com.kick.npl.data.repository.ParkingLotsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingLotDetailViewModel @Inject constructor(
    private val parkingLotsRepository: ParkingLotsRepository,
    private val userIdDataSource: UserIdDataSource
) : ViewModel() {
    private val userId by lazy { userIdDataSource.getUserId() }

    var isLoading by mutableStateOf(false)
        private set

    var paymentResult: String? by mutableStateOf(null)
        private set

    private val _result = MutableSharedFlow<PaymentResult>()
    val result = _result.asSharedFlow()

    fun reserveParkingLot(parkingLotId: String) = viewModelScope.launch(Dispatchers.IO) {
        isLoading = true

        userId?.let  {
            if (parkingLotsRepository.getParkingLot(parkingLotId)?.reserved?.isNotBlank() == false) {
                parkingLotsRepository.reserve(parkingLotId, it)
                _result.emit(PaymentResult.Success)
            } else {
                _result.emit(PaymentResult.Failure("주차장이 이미 사용중입니다."))
            }
        }

        isLoading = false
    }

    sealed class PaymentResult {
        data object Success : PaymentResult()
        data class Failure(val message: String) : PaymentResult()
    }
}
