package com.example.bismillahtestiprojectucp.service

import com.example.bismillahtestiprojectucp.model.Acara
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AcaraService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data acara
    @GET("acara/read.php")
    suspend fun getAcara(): List<Acara>

    // Mengambil satu data acara berdasarkan id_acara
    @GET("acara/read1.php")
    suspend fun getAcaraById(@Query("id_acara") idAcara: String): Acara

    // Menambahkan data acara baru
    @POST("acara/create.php")
    suspend fun insertAcara(@Body acara: Acara): Response<Void>

    // Mengupdate data acara berdasarkan id_acara
    @PUT("acara/update.php")
    suspend fun updateAcara(
        @Query("id_acara") idAcara: String,
        @Body acara: Acara
    ): Response<Void>

    // Menghapus data acara berdasarkan id_acara
    @DELETE("acara/delete.php")
    suspend fun deleteAcara(@Query("id_acara") idAcara: String): Response<Void>
}
