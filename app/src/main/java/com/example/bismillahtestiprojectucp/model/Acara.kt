package com.example.bismillahtestiprojectucp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Acara(

    @SerialName("id_acara")
    val idAcara: String,

    @SerialName("nama_acara")
    val namaAcara: String,

    @SerialName("deskripsi_acara")
    val deskripsiAcara: String,

    @SerialName("tanggal_mulai")
    val tanggalMulai: String,

    @SerialName("tanggal_berakhir")
    val tanggalBerakhir: String,

    @SerialName("id_lokasi")
    val idLokasi: String,

    @SerialName("id_klien")
    val idKlien: String,

    @SerialName("status_acara")
    val statusAcara: String
)
