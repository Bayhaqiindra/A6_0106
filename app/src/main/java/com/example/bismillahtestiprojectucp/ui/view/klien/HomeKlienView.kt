package com.example.bismillahtestiprojectucp.ui.view.klien

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.customwidget.CostumeTopAppBar
import com.example.bismillahtestiprojectucp.ui.view.acara.OnError
import com.example.bismillahtestiprojectucp.ui.view.acara.OnLoading
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.HomeKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienUiState

object DestinasiKlienHome : DestinasiNavigasi {
    override val route = "klien_home"
    override val titleRes = "Home Klien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KlienHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getKlien()
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiKlienHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKlien()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Klien")
            }
        }
    ) { innerPadding ->
        KlienStatus(
            klienUiState = viewModel.klienUiState,
            retryAction = { viewModel.getKlien() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun KlienStatus(
    klienUiState: KlienUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (klienUiState) {
        is KlienUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is KlienUiState.Success -> {
            if (klienUiState.klien.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Klien")
                }
            } else {
                KlienLayout(
                    klien = klienUiState.klien,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idKlien) },
                )
            }
        }
        is KlienUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KlienLayout(
    klien: List<Klien>,
    modifier: Modifier = Modifier,
    onDetailClick: (Klien) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(klien) { klien ->
            KlienCard(
                klien = klien,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(klien) }
            )
        }
    }
}

@Composable
fun KlienCard(
    klien: Klien,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = klien.namaKlien,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
            }

            Text(
                text = "Kontak: ${klien.kontakKlien}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

