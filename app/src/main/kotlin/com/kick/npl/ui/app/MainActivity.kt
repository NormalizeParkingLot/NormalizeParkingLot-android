package com.kick.npl.ui.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.local.UserIdDataSource
import com.kick.npl.ui.app.NPLApp
import com.kick.npl.ui.theme.NPLTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userIdDataSource: UserIdDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("TEST", "사용자 정보 요청 실패", error)
            }
            else if (user?.kakaoAccount?.email != null) {
                user.kakaoAccount?.email?.let {
                    userIdDataSource.setUserId(it)
                }
            }
        }

        setContent { NPLApp() }
    }
}