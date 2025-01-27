package com.example.bismillahtestiprojectucp.ui.view.lokasi

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.UpdateLokasiViewModel
import kotlinx.coroutines.launch


object DestinasiUpdateLokasi : DestinasiNavigasi {
    override val route = "update_lokasi"
    override val titleRes = "Update Data Lokasi"
    const val idLokasi = "idLokasi"
    val routeWithArgs = "$route/{$idLokasi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateLokasiView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CostumeTopAppBar(
//                title = DestinasiUpdateLokasi.titleRes,
//                canNavigateBack = true,
//                navigateUp = onNavigateUp
//            )
//        }
    ) { innerPadding ->
        EntryBodyLokasi(
            insertLokasiUiState = viewModel.uilokasiState,
            onLokasiValueChange = viewModel::updateInsertLokasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateLokasi() // Menyimpan data pekerja
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