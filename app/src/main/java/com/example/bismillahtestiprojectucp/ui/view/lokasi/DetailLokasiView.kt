package com.example.bismillahtestiprojectucp.ui.view.lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.model.Lokasi
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.DetailLokasiUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.DetailLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel

object DestinasiDetailLokasi : DestinasiNavigasi {
    override val route = "detail_lokasi"
    override val titleRes = "Detail Lokasi"
    const val idLokasi = "idLokasi"
    val routeWithArgs = "$route/{$idLokasi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLokasiView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailLokasiViewModel: DetailLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        detailLokasiViewModel.getLokasiById()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary)),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Detail Lokasi",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 35.sp
                ),
                color = Color.White,
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(110.dp),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(color = colorResource(R.color.yellow))
                .offset(y = -10.dp)
                .padding(top = 8.dp, bottom = 40.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                DetailLokasiContent(
                    detailLokasiUiState = detailLokasiViewModel.detailLokasiUiState,
                    modifier = Modifier.fillMaxWidth(),
                    onDeleteClick = {
                        detailLokasiViewModel.deleteLokasi()
                        navigateBack()
                    }
                )
            }

            FloatingActionButton(
                onClick = {
                    val idLokasi = (detailLokasiViewModel.detailLokasiUiState as? DetailLokasiUiState.Success)?.lokasi?.idLokasi
                    if (idLokasi != null) onEditClick(idLokasi)
                },
                shape = RoundedCornerShape(20.dp),
                containerColor = colorResource(R.color.primary),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Lokasi",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun DetailLokasiContent(
    detailLokasiUiState: DetailLokasiUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (detailLokasiUiState) {
        is DetailLokasiUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailLokasiUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is DetailLokasiUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailLokasiCard(lokasi = detailLokasiUiState.lokasi)
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary),
                        contentColor = Color.White,
                    )
                ) {
                    Text("Delete")
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
fun DetailLokasiCard(
    lokasi: Lokasi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.primary)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailLokasi(judul = "ID Lokasi", isinya = lokasi.idLokasi)
            ComponentDetailLokasi(judul = "Nama Lokasi", isinya = lokasi.namaLokasi)
            ComponentDetailLokasi(judul = "Alamat Lokasi", isinya = lokasi.alamatLokasi)
            ComponentDetailLokasi(judul = "Kapasitas", isinya = lokasi.kapasitas)
        }
    }
}

@Composable
fun ComponentDetailLokasi(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.yellow)
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White
            )
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}


