package com.example.bismillahtestiprojectucp.repository

import com.example.bismillahtestiprojectucp.model.Lokasi
import com.example.bismillahtestiprojectucp.service.LokasiService
import java.io.IOException

interface LokasiRepository {
    suspend fun getLokasi(): List<Lokasi>
    suspend fun insertLokasi(lokasi: Lokasi)
    suspend fun updateLokasi(idLokasi: String, lokasi: Lokasi)
    suspend fun deleteLokasi(idLokasi: String)
    suspend fun getLokasiById(idLokasi: String): Lokasi
}

class NetworkLokasiRepository(
    private val lokasiApiService: LokasiService
) : LokasiRepository {

    override suspend fun insertLokasi(lokasi: Lokasi) {
        lokasiApiService.insertLokasi(lokasi)
    }

    override suspend fun updateLokasi(idLokasi: String, lokasi: Lokasi) {
        lokasiApiService.updateLokasi(idLokasi, lokasi)
    }

    override suspend fun deleteLokasi(idLokasi: String) {
        try {
            val response = lokasiApiService.deleteLokasi(idLokasi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete lokasi. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getLokasi(): List<Lokasi> = lokasiApiService.getLokasi()

    override suspend fun getLokasiById(idLokasi: String): Lokasi {
        try {
            return lokasiApiService.getLokasiById(idLokasi)
        } catch (e: IOException) {
            throw IOException("Failed to fetch lokasi with ID: $idLokasi. Network error occurred.", e)
        }
    }
}