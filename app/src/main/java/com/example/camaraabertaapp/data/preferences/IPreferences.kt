package com.example.camaraabertaapp.data.preferences

interface IPreferences {

    suspend fun getIsOnboardFinished(key: String): Boolean

    suspend fun setIsOnboardFinished(key: String, value: Boolean)

}