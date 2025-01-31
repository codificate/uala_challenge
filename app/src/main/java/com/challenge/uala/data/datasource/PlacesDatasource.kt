package com.challenge.uala.data.datasource

import android.content.res.Resources
import androidx.annotation.RawRes
import javax.inject.Inject

class PlacesDatasource @Inject constructor(private val resources: Resources) {
    fun readJsonFromRaw(@RawRes resourceId: Int): String? {
        return try {
            val inputStream = resources.openRawResource(resourceId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: Exception) {
            null
        }
    }
}