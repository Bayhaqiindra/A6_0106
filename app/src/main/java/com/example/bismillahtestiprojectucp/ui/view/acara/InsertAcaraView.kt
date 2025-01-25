package com.example.bismillahtestiprojectucp.ui.view.acara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.customwidget.CostumeTopAppBar
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.AcaraPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.HomeAcaraUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.HomeKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiUiState
import com.example.bismillahtestiprojectucp.ui.widget.DynamicSelectedTextField
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiEvent
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiState
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraViewModel
import kotlinx.coroutines.launch

object DestinasiAcaraInsert : DestinasiNavigasi {
    override val route = "insert_acara"
    override val titleRes = "Masukan Data Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAcaraScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: InsertAcaraViewModel = viewModel(factory = AcaraPenyediaViewModel.Factory),
    viewModelLokasi: HomeLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
    viewModelKlien: HomeKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val klienUiState = viewModelKlien.klienUiState
    val lokasiUiState = viewModelLokasi.lokasiUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAcaraInsert.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        EntryBodyAcara(
            insertAcaraUiState = viewModel.uiacaraState,
            onAcaraValueChange = viewModel::updateInsertAcaraState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAcara()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyAcara(
    insertAcaraUiState: InsertAcaraUiState,
    onAcaraValueChange: (InsertAcaraUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAcara(
            insertAcaraUiEvent = insertAcaraUiState.insertUiEvent,
            onValueChange = onAcaraValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelLokasiViewModel = viewModel(),
            viewModelKlienViewModel = viewModel()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertAcaraUiState.insertUiEvent.idKlien.isNotEmpty()
                    && insertAcaraUiState.insertUiEvent.idLokasi.isNotEmpty()// Validasi form
        ) {
            if (insertAcaraUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputAcara(
    insertAcaraUiEvent: InsertAcaraUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAcaraUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelKlienViewModel: HomeKlienViewModel,
    viewModelLokasiViewModel: HomeLokasiViewModel// Menambahkan parameter untuk TanamanViewModel
) {
    // Ambil data tanaman dari HomeTanamanViewModel
    val lokasiUiState = viewModelLokasiViewModel.lokasiUiState
    val klienUiState = viewModelKlienViewModel.klienUiState

    // Menampilkan UI berdasarkan state
    when (klienUiState) {
        is KlienUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is KlienUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
        }

        is KlienUiState.Success -> {
            val klienList = klienUiState.klien // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = insertAcaraUiEvent.namaAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(namaAcara = it)) },
                    label = { Text("Nama Acara") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                OutlinedTextField(
                    value = insertAcaraUiEvent.deskripsiAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(deskripsiAcara = it)) },
                    label = { Text("Deskripsi Acara") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = insertAcaraUiEvent.tanggalMulai,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(tanggalMulai = it)) },
                    label = { Text("Tanggal Mulai") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = insertAcaraUiEvent.tanggalBerakhir,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(tanggalBerakhir = it)) },
                    label = { Text("Tanggal Berakhir") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                DynamicSelectedTextField(
                    selectedValue = insertAcaraUiEvent.idKlien.toString(), // Mengonversi idTanaman ke String
                    options = klienList.map { it.idKlien.toString() },
                    label = "Pilih ID Klien",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertAcaraUiEvent.copy(idKlien = selectedId))
                    }
                )

                OutlinedTextField(
                    value = insertAcaraUiEvent.statusAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(statusAcara = it)) },
                    label = { Text("Status Acara") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
            }
        }
    }

    when (lokasiUiState) {
        is LokasiUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is LokasiUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
        }

        is LokasiUiState.Success -> {
            val lokasiList = lokasiUiState.lokasi // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DynamicSelectedTextField(
                    selectedValue = insertAcaraUiEvent.idLokasi.toString(), // Mengonversi idTanaman ke String
                    options = lokasiList.map { it.idLokasi.toString() }, // Mengonversi ID tanaman ke String
                    label = "Pilih ID Lokasi",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertAcaraUiEvent.copy(idLokasi = selectedId))
                    }
                )
            }
        }
    }
}