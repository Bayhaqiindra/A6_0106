package com.example.bismillahtestiprojectucp.ui.view.klien

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
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraInsert
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.AcaraPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.HomeKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiUiState
import com.example.bismillahtestiprojectucp.ui.widget.DynamicSelectedTextField
import kotlinx.coroutines.launch

    object DestinasiKlienInsert : DestinasiNavigasi {
        override val route = "insert_klien"
        override val titleRes = "Masukan Data Klien"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InsertKlienScreen(
        navigateBack: () -> Unit,
        modifier: Modifier = Modifier,
        viewModel: InsertKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory),
    ) {
        val coroutineScope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CostumeTopAppBar(
                    title = DestinasiKlienInsert.titleRes,
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { innerPadding ->
            EntryBodyKlien(
                insertKlienUiState = viewModel.uiklienState,
                onKlienValueChange = viewModel::updateInsertKlienState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertKlien() // Menyimpan data pekerja
                        navigateBack() // Navigasi kembali ke HomePekerja
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
    fun EntryBodyKlien(
        insertKlienUiState: InsertKlienUiState,
        onKlienValueChange: (InsertKlienUiEvent) -> Unit,
        onSaveClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = modifier.padding(12.dp)
        ) {
            FormInputKlien(
                insertKlienUiEvent = insertKlienUiState.insertUiEvent,
                onValueChange = onKlienValueChange,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = onSaveClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                enabled = insertKlienUiState.insertUiEvent.idKlien.isNotEmpty() // Validasi form
            ) {
                if (insertKlienUiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
                } else {
                    Text(text = "Simpan")
                }
            }
        }
    }

    @Composable
    fun FormInputKlien(
        insertKlienUiEvent: InsertKlienUiEvent,
        modifier: Modifier = Modifier,
        onValueChange: (InsertKlienUiEvent) -> Unit = {},
        enabled: Boolean = true,
    ) { // Mendapatkan list tanaman
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = insertKlienUiEvent.idKlien,
                onValueChange = { onValueChange(insertKlienUiEvent.copy(idKlien = it)) },
                label = { Text("ID Klien") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )

            OutlinedTextField(
                value = insertKlienUiEvent.namaKlien,
                onValueChange = { onValueChange(insertKlienUiEvent.copy(namaKlien = it)) },
                label = { Text("Nama Klien") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = insertKlienUiEvent.kontakKlien,
                onValueChange = { onValueChange(insertKlienUiEvent.copy(kontakKlien = it)) },
                label = { Text("Kontak Klien") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
    }