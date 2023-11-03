package com.kick.npl.ui.manage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.local.UserIdDataSource
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagingViewModel @Inject constructor(
    private val parkingLotsRepository: ParkingLotsRepository,
    private val userIdDataSource: UserIdDataSource
) : ViewModel() {

    private val userId by lazy { userIdDataSource.getUserId() }

    var myReservedParkingLots by mutableStateOf<List<ParkingLotData>>(emptyList())

    var myRegisteredParkingLots by mutableStateOf<List<ParkingLotData>>(emptyList())

    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)

            val allParkingLot = parkingLotsRepository.getAllParkingLots()

            myReservedParkingLots = allParkingLot
                ?.filter { it.reserved == userId }
                ?: emptyList()

            myRegisteredParkingLots = allParkingLot
                ?.filter { it.provider == userId }
                ?: emptyList()

            _isLoading.emit(false)
        }
    }

    fun setBlock(id: String, block: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            if (parkingLotsRepository.getParkingLot(id)?.isOccupied == false) {
                parkingLotsRepository.setIsBlocked(id, block)
                val toastText = if (block) "주차장 차단기가 닫혔습니다." else "주차장 차단기가 열렸습니다."
                _toast.emit(toastText)
            } else {
                _toast.emit("차량이 주차되어있어 수행할 수 없습니다.")
            }
            _isLoading.emit(false)
        }
    }
}
