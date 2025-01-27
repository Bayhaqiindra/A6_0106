package com.example.bismillahtestiprojectucp.ui.view.acara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
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

    LaunchedEffect(Unit) {
        viewModel.getAcara()
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.primary)),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Selamat Datang!",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 35.sp),
                    color = Color.White,
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp),
                )
            }
            Text(
                text = "Berikut ini adalah Daftar Acara",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif

                ),
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)) // Membentuk lengkungan
                .background(color = colorResource(R.color.yellow))
                .offset(y=-10.dp)
                .padding(top = 8.dp, bottom = 40.dp)
            ) {
                Column(modifier = Modifier) {
                    AcaraStatus(
                        acaraHomeUiState = viewModel.acaraHomeUIState,
                        retryAction = { viewModel.getAcara() },
                        modifier = Modifier,
                        onDetailClick = onDetailClick,
                    )
                }
                FloatingActionButton(
                    onClick = navigateToItemEntry,
                    shape = RoundedCornerShape(20.dp), // Bentuk bulat
                    containerColor = colorResource(R.color.primary), // Warna primary
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Posisi di kanan bawah
                        .padding(24.dp) // Padding dari tepi layar
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Acara",
                        tint = Color.White // Warna ikon menjadi putih untuk kontras
                    )
                }
            }

        }
    }


@Composable
fun AcaraStatus(
    acaraHomeUiState: HomeAcaraUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
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
                    modifier = modifier,
                    onDetailClick = { onDetailClick(it.idAcara.toString()) },
                )
            }
        }
        is HomeAcaraUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorScheme.primary)
    }
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
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction) {
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
            modifier = Modifier.fillMaxHeight(), // Mengisi tinggi penuh
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
        modifier = modifier
            .padding(6.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp)) // Tambahkan bayangan
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.primary) // Warna utama
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
        ) {
            // Latar belakang berupa dekorasi
            Image(
                painter = painterResource(id = R.drawable.logo), // Ganti dengan id gambar Anda
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.5f // Transparansi
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Baris atas dengan judul dan tanggal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = acara.namaAcara,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Baris untuk tanggal
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange, // Ikon tanggal
                            contentDescription = "Date Icon",
                            tint = Color.White, // Warna emas
                            modifier = Modifier.size(15.dp) // Ukuran ikon lebih kecil
                        )
                        Spacer(modifier = Modifier.width(5.dp)) // Jarak lebih kecil untuk elemen tanggal
                        Text(
                            text = acara.tanggalMulai,
                            style = MaterialTheme.typography.bodySmall, // Gaya teks lebih kecil
                            color = Color.White
                        )
                    }
                }

                // Tambahkan garis pemisah
                Divider(
                    color = colorResource(R.color.yellow),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth() // Garis memenuhi lebar kartu// Jarak kecil antara garis dan konten
                )

                // Informasi tambahan: Lokasi
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place, // Ikon lokasi
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Lokasi: ${acara.idLokasi}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // Ikon lokasi
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Klien: ${acara.idKlien}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                // Informasi tambahan: Status
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info, // Ikon status
                        contentDescription = "Status Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Status: ${acara.statusAcara}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Panah ke kanan di pojok bawah
            Icon(
                imageVector = Icons.Default.ArrowForward, // Ikon panah ke kanan
                contentDescription = "Arrow Icon",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Posisikan di pojok kanan bawah
                    .padding(15.dp) // Beri jarak dari tepi
                    .size(25.dp) // Ukuran ikon
            )
        }
    }
}
