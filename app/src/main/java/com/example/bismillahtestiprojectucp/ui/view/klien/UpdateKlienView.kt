package com.example.bismillahtestiprojectucp.ui.view.klien

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.UpdateKlienViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKlien : DestinasiNavigasi {
    override val route = "update_klien"
    override val titleRes = "Update Data Klien"
    const val idKlien = "idKlien"
    val routeWithArgs = "$route/{$idKlien}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKlienView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory),
) {


    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CostumeTopAppBar(
//                title = DestinasiUpdateKlien.titleRes,
//                canNavigateBack = true,
//                navigateUp = onNavigateUp
//            )
//        }
    ) { innerPadding ->
        EntryBodyKlien(
            insertKlienUiState = viewModel.uiklienState,
            onKlienValueChange = viewModel::updateInsertKlienState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKlien() // Menyimpan data pekerja
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