package com.kick.npl.ui.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.ui.util.UiState
import com.kick.npl.ui.util.getIdleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _result = MutableStateFlow(getIdleUiState<Unit>())
    val result = _result.asStateFlow()
    fun loginWithKakaoTalk(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            viewModelScope.launch(Dispatchers.IO) {
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@launch
                    } else {
                        _result.update { UiState.Error("로그인에 실패하였습니다") }
                    }
                } else {
                    _result.update { UiState.Success(Unit) }
                }
            }
        }
    }

    fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            viewModelScope.launch(Dispatchers.IO) {
                if (error != null) {
                    _result.update { UiState.Error("로그인에 실패하였습니다") }
                } else if (token == null) {
                    _result.update { UiState.Error("사용자 토큰이 존재하지 않습니다") }
                } else {
                    _result.update { UiState.Success(Unit) }
                }
            }
        }
    }
}
