package com.example.bismillahtestiprojectucp.dependeciesinjection

import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import com.example.bismillahtestiprojectucp.repository.LokasiRepository
import com.example.bismillahtestiprojectucp.repository.NetworkAcaraRepository
import com.example.bismillahtestiprojectucp.repository.NetworkKlienRepository
import com.example.bismillahtestiprojectucp.repository.NetworkLokasiRepository
import com.example.bismillahtestiprojectucp.repository.NetworkVendorRepository
import com.example.bismillahtestiprojectucp.repository.VendorRepository
import com.example.bismillahtestiprojectucp.service.AcaraService
import com.example.bismillahtestiprojectucp.service.KlienService
import com.example.bismillahtestiprojectucp.service.LokasiService
import com.example.bismillahtestiprojectucp.service.VendorService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val acaraRepository: AcaraRepository
    val klienRepository: KlienRepository
    val lokasiRepository: LokasiRepository
    val vendorRepository: VendorRepository
}

class EventContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2/finalpam/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val acaraService: AcaraService by lazy { retrofit.create(AcaraService::class.java) }
    override val acaraRepository: AcaraRepository by lazy { NetworkAcaraRepository(acaraService) }

    private val klienService: KlienService by lazy { retrofit.create(KlienService::class.java) }
    override val klienRepository: KlienRepository by lazy { NetworkKlienRepository(klienService) }

    private val lokasiService: LokasiService by lazy { retrofit.create(LokasiService::class.java) }
    override val lokasiRepository: LokasiRepository by lazy { NetworkLokasiRepository(lokasiService) }

    private val vendorService: VendorService by lazy { retrofit.create(VendorService::class.java) }
    override val vendorRepository: VendorRepository by lazy { NetworkVendorRepository(vendorService) }
}

