package com.kick.npl.usecase

import android.util.Log
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor() {
    fun getUserId() : String {
        var id = ""
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("TEST", "사용자 정보 요청 실패", error)
            }
            else if (user?.id != null) {
                user?.kakaoAccount?.email?.let { id = it }
            }
        }
        return id
    }
}