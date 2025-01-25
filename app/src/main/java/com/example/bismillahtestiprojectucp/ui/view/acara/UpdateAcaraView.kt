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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.customwidget.CostumeTopAppBar
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.AcaraPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.UpdateAcaraViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.HomeKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel

import kotlinx.coroutines.launch

object DestinasiUpdateAcara : DestinasiNavigasi {
    override val route = "update_acara"
    override val titleRes = "Update Data Acara"
    const val idAcara = "idAcara"
    val routeWithArgs = "$route/{$idAcara}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAcaraView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateAcaraViewModel = viewModel(factory = AcaraPenyediaViewModel.Factory),
    viewModelLokasi: HomeLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
    viewModelKlien: HomeKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAcara.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBodyAcara(
            insertAcaraUiState = viewModel.uiacaraState,
            onAcaraValueChange = viewModel::updateInsertAcaraState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAcara() // Menyimpan data pekerja
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
