package com.example.bismillahtestiprojectucp.service

import com.example.bismillahtestiprojectucp.model.Vendor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface VendorService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data vendor
    @GET("vendor/read.php")
    suspend fun getVendor(): List<Vendor>

    // Mengambil satu data vendor berdasarkan id_vendor
    @GET("vendor/read1.php")
    suspend fun getVendorById(@Query("id_vendor") idVendor: String): Vendor

    // Menambahkan data vendor baru
    @POST("vendor/create.php")
    suspend fun insertVendor(@Body vendor: Vendor): Response<Void>

    // Mengupdate data vendor berdasarkan id_vendor
    @PUT("vendor/update.php")
    suspend fun updateVendor(
        @Query("id_vendor") idVendor: String,
        @Body vendor: Vendor
    ): Response<Void>

    // Menghapus data vendor berdasarkan id_vendor
    @DELETE("vendor/delete.php")
    suspend fun deleteVendor(@Query("id_vendor") idVendor: String): Response<Void>
}