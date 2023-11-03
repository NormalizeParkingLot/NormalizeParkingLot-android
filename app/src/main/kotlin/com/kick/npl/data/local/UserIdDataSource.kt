package com.kick.npl.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class UserIdDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val keyUserId = "userId"

    fun getUserId(): String? = sharedPreferences.getString(keyUserId, null)

    fun setUserId(id: String) {
        with (sharedPreferences.edit()) {
            putString(keyUserId, id)
            apply()
        }
    }
}