package com.example.bismillahtestiprojectucp.repository

import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.service.AcaraService
import java.io.IOException

interface AcaraRepository {
    suspend fun getAcara(): List<Acara>
    suspend fun insertAcara(acara: Acara)
    suspend fun updateAcara(idAcara: String, acara: Acara)
    suspend fun deleteAcara(idAcara: String)
    suspend fun getAcaraById(idAcara: String): Acara
}

class NetworkAcaraRepository(
    private val acaraApiService: AcaraService
) : AcaraRepository {

    override suspend fun insertAcara(acara: Acara) {
        acaraApiService.insertAcara(acara)
    }

    override suspend fun updateAcara(idAcara: String, acara: Acara) {
        acaraApiService.updateAcara(idAcara, acara)
    }

    override suspend fun deleteAcara(idAcara: String) {
        try {
            val response = acaraApiService.deleteAcara(idAcara)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete acara. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAcara(): List<Acara> = acaraApiService.getAcara()

    override suspend fun getAcaraById(idAcara: String): Acara {
        try {
            return acaraApiService.getAcaraById(idAcara)
        } catch (e: IOException) {
            throw IOException("Failed to fetch acara with ID: $idAcara. Network error occurred.", e)
        }
    }
}
