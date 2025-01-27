package com.example.bismillahtestiprojectucp.ui.view.vendor

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.UpdateVendorViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateVendor : DestinasiNavigasi {
    override val route = "update_vendor"
    override val titleRes = "Update Data Vendor"
    const val idVendor = "idLokasi"
    val routeWithArgs = "$route/{$idVendor}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateVendorView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateVendorViewModel = viewModel(factory = VendorPenyediaViewModel.Factory),
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        EntryBodyVendor(
            insertVendorUiState = viewModel.uivendorState,
            onVendorValueChange = viewModel::updateInsertVendorState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateVendor() // Menyimpan data pekerja
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