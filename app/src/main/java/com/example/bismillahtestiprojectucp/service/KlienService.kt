package com.example.bismillahtestiprojectucp.service

import com.example.bismillahtestiprojectucp.model.Klien
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KlienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mengambil semua data klien
    @GET("klien/read.php")
    suspend fun getKlien(): List<Klien>

    // Mengambil satu data klien berdasarkan id_klien
    @GET("klien/read1.php")
    suspend fun getKlienById(@Query("id_klien") idKlien: String): Klien

    // Menambahkan data klien baru
    @POST("klien/create.php")
    suspend fun insertKlien(@Body klien: Klien): Response<Void>

    // Mengupdate data klien berdasarkan id_klien
    @PUT("klien/update.php")
    suspend fun updateKlien(
        @Query("id_klien") idKlien: String,
        @Body klien: Klien
    ): Response<Void>

    // Menghapus data klien berdasarkan id_klien
    @DELETE("klien/delete.php")
    suspend fun deleteKlien(@Query("id_klien") idKlien: String): Response<Void>
}