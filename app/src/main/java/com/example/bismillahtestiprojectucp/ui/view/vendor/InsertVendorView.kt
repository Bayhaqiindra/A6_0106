package com.example.bismillahtestiprojectucp.ui.view.vendor

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiVendorInsert : DestinasiNavigasi {
    override val route = "insert_vendor"
    override val titleRes = "Masukan Data Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVendorScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertVendorViewModel = viewModel(factory = VendorPenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiVendorInsert.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        EntryBodyVendor(
            insertVendorUiState = viewModel.uiVendorState,
            onVendorValueChange = viewModel::updateInsertVendorState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertVendor() // Menyimpan data vendor
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
fun EntryBodyVendor(
    insertVendorUiState: InsertVendorUiState,
    onVendorValueChange: (InsertVendorUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputVendor(
            insertVendorUiEvent = insertVendorUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertVendorUiState.insertUiEvent.idVendor.isNotEmpty() // Validasi form
        ) {
            if (insertVendorUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputVendor(
    insertVendorUiEvent: InsertVendorUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertVendorUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertVendorUiEvent.idVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(idVendor = it)) },
            label = { Text("ID Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertVendorUiEvent.namaVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(namaVendor = it)) },
            label = { Text("Nama Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertVendorUiEvent.jenisVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(jenisVendor = it)) },
            label = { Text("Jenis Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertVendorUiEvent.kontakVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(kontakVendor = it)) },
            label = { Text("Kontak Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
