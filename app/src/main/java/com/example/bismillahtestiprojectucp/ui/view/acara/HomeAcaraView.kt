package com.example.bismillahtestiprojectucp.ui.view.acara

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.customwidget.CostumeTopAppBar
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.AcaraPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.HomeAcaraUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.HomeAcaraViewModel

object DestinasiAcaraHome : DestinasiNavigasi {
    override val route = "acara_home"
    override val titleRes = "Home Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcaraHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeAcaraViewModel = viewModel(factory = AcaraPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getAcara()
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAcaraHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAcara()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Acara")
            }
        }
    ) { innerPadding ->
        AcaraStatus(
            acaraHomeUiState = viewModel.acaraHomeUIState,
            retryAction = { viewModel.getAcara() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
        )
    }
}

@Composable
fun AcaraStatus(
    acaraHomeUiState: HomeAcaraUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
){
    when (acaraHomeUiState) {
        is HomeAcaraUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeAcaraUiState.Success -> {
            if (acaraHomeUiState.acara.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Acara")
                }
            } else {
                AcaraLayout(
                    acara = acaraHomeUiState.acara,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idAcara.toString()) },
                )
            }
        }
        is HomeAcaraUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(20.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "",
        )
        Text(text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AcaraLayout(
    acara: List<Acara>,
    modifier: Modifier = Modifier,
    onDetailClick: (Acara) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(acara) { acara ->
            AcaraCard(
                acara = acara,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(acara) }
            )
        }
    }
}

@Composable
fun AcaraCard(
    acara: Acara,
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
                    text = acara.namaAcara,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = acara.tanggalMulai,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = acara.deskripsiAcara,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Lokasi: ${acara.idLokasi}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Status: ${acara.statusAcara}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
