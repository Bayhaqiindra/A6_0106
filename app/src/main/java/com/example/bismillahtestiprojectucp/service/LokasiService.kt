package com.example.bismillahtestiprojectucp.service

import com.example.bismillahtestiprojectucp.model.Lokasi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LokasiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data lokasi
    @GET("lokasi/read.php")
    suspend fun getLokasi(): List<Lokasi>

    // Mengambil satu data lokasi berdasarkan id_lokasi
    @GET("lokasi/read1.php")
    suspend fun getLokasiById(@Query("id_lokasi") idLokasi: String): Lokasi

    // Menambahkan data lokasi baru
    @POST("lokasi/create.php")
    suspend fun insertLokasi(@Body lokasi: Lokasi): Response<Void>

    // Mengupdate data lokasi berdasarkan id_lokasi
    @PUT("lokasi/update.php")
    suspend fun updateLokasi(
        @Query("id_lokasi") idLokasi: String,
        @Body lokasi: Lokasi
    ): Response<Void>

    // Menghapus data lokasi berdasarkan id_lokasi
    @DELETE("lokasi/delete.php")
    suspend fun deleteLokasi(@Query("id_lokasi") idLokasi: String): Response<Void>
}