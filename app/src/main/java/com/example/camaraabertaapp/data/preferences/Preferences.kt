package com.example.camaraabertaapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "app_settings"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class Preferences @Inject constructor(
    private val context: Context
) : IPreferences {

    override suspend fun getIsOnboardFinished(key: String): Boolean {
        return try {
            val preferenceKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey] ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun setIsOnboardFinished(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

}