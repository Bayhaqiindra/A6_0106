package com.example.bismillahtestiprojectucp.ui.view.vendor

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
import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.customwidget.CostumeTopAppBar
import com.example.bismillahtestiprojectucp.ui.view.acara.OnError
import com.example.bismillahtestiprojectucp.ui.view.acara.OnLoading
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.HomeVendorViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorUiState

object DestinasiVendorHome : DestinasiNavigasi {
    override val route = "vendor_home"
    override val titleRes = "Home Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeVendorViewModel = viewModel(factory = VendorPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getVendor()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiVendorHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getVendor()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vendor")
            }
        }
    ) { innerPadding ->
        VendorStatus(
            vendorUiState = viewModel.vendorUiState,
            retryAction = { viewModel.getVendor() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
        )
    }
}

@Composable
fun VendorStatus(
    vendorUiState: VendorUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (vendorUiState) {
        is VendorUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is VendorUiState.Success -> {
            if (vendorUiState.vendor.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Vendor")
                }
            } else {
                VendorLayout(
                    vendor = vendorUiState.vendor,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idVendor) }
                )
            }
        }
        is VendorUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun VendorLayout(
    vendor: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailClick: (Vendor) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendor) { vendor ->
            VendorCard(
                vendor = vendor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(vendor) }
            )
        }
    }
}

@Composable
fun VendorCard(
    vendor: Vendor,
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
                    text = vendor.namaVendor,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
            }

            Text(
                text = "Jenis Vendor: ${vendor.jenisVendor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Kontak: ${vendor.kontakVendor}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
