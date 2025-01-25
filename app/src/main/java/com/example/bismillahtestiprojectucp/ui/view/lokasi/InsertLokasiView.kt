package com.example.bismillahtestiprojectucp.ui.view.lokasi

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiLokasiInsert : DestinasiNavigasi {
    override val route = "insert_lokasi"
    override val titleRes = "Masukan Data Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertLokasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiLokasiInsert.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        EntryBodyLokasi(
            insertLokasiUiState = viewModel.uilokasiState,
            onLokasiValueChange = viewModel::updateInsertLokasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertLokasi() // Menyimpan data lokasi
                    navigateBack() // Navigasi kembali
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
fun EntryBodyLokasi(
    insertLokasiUiState: InsertLokasiUiState,
    onLokasiValueChange: (InsertLokasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputLokasi(
            insertLokasiUiEvent = insertLokasiUiState.insertUiEvent,
            onValueChange = onLokasiValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertLokasiUiState.insertUiEvent.idLokasi.isNotEmpty() // Validasi form
        ) {
            if (insertLokasiUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputLokasi(
    insertLokasiUiEvent: InsertLokasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertLokasiUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertLokasiUiEvent.idLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(idLokasi = it)) },
            label = { Text("ID Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.namaLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(namaLokasi = it)) },
            label = { Text("Nama Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.alamatLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(alamatLokasi = it)) },
            label = { Text("Alamat Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.kapasitas,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
