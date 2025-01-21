package com.example.bismillahtestiprojectucp.repository

import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.service.KlienService
import java.io.IOException

interface KlienRepository {
    suspend fun getKlien(): List<Klien>
    suspend fun insertKlien(klien: Klien)
    suspend fun updateKlien(idKlien: String, klien: Klien)
    suspend fun deleteKlien(idKlien: String)
    suspend fun getKlienById(idKlien: String): Klien
}

class NetworkKlienRepository(
    private val klienApiService: KlienService
) : KlienRepository {

    override suspend fun insertKlien(klien: Klien) {
        klienApiService.insertKlien(klien)
    }

    override suspend fun updateKlien(idKlien: String, klien: Klien) {
        klienApiService.updateKlien(idKlien, klien)
    }

    override suspend fun deleteKlien(idKlien: String) {
        try {
            val response = klienApiService.deleteKlien(idKlien)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete klien. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKlien(): List<Klien> = klienApiService.getKlien()

    override suspend fun getKlienById(idKlien: String): Klien {
        try {
            return klienApiService.getKlienById(idKlien)
        } catch (e: IOException) {
            throw IOException("Failed to fetch klien with ID: $idKlien. Network error occurred.", e)
        }
    }
}