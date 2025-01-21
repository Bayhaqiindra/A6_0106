package com.example.bismillahtestiprojectucp.repository

import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.service.VendorService
import java.io.IOException

interface VendorRepository {
    suspend fun getVendor(): List<Vendor>
    suspend fun insertVendor(vendor: Vendor)
    suspend fun updateVendor(idVendor: String, vendor: Vendor)
    suspend fun deleteVendor(idVendor: String)
    suspend fun getVendorById(idVendor: String): Vendor
}

class NetworkVendorRepository(
    private val vendorApiService: VendorService
) : VendorRepository {

    override suspend fun insertVendor(vendor: Vendor) {
        vendorApiService.insertVendor(vendor)
    }

    override suspend fun updateVendor(idVendor: String, vendor: Vendor) {
        vendorApiService.updateVendor(idVendor, vendor)
    }

    override suspend fun deleteVendor(idVendor: String) {
        try {
            val response = vendorApiService.deleteVendor(idVendor)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete vendor. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getVendor(): List<Vendor> = vendorApiService.getVendor()

    override suspend fun getVendorById(idVendor: String): Vendor {
        try {
            return vendorApiService.getVendorById(idVendor)
        } catch (e: IOException) {
            throw IOException("Failed to fetch vendor with ID: $idVendor. Network error occurred.", e)
        }
    }
}