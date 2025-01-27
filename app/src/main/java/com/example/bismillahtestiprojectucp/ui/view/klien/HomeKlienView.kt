package com.example.bismillahtestiprojectucp.ui.view.klien

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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
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

    LaunchedEffect(Unit) {
        viewModel.getKlien()
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
                text = "Daftar Klien",
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
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)) // Membentuk lengkungan
                .background(color = colorResource(R.color.yellow))
                .offset(y = -10.dp)
                .padding(top = 8.dp, bottom = 40.dp)
        ) {
            Column(modifier = Modifier) {
                KlienStatus(
                    klienUiState = viewModel.klienUiState,
                    retryAction = { viewModel.getKlien() },
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
                    contentDescription = "Add Klien",
                    tint = Color.White // Warna ikon menjadi putih untuk kontras
                )
            }
        }
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
                    modifier = modifier,
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
        modifier = Modifier.fillMaxHeight(), // Mengisi tinggi penuh
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
                .height(120.dp)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = klien.namaKlien,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Baris untuk tanggal
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu, // Ikon tanggal
                            contentDescription = "",
                            tint = Color.White, // Warna emas
                            modifier = Modifier.size(15.dp) // Ukuran ikon lebih kecil
                        )
                        Spacer(modifier = Modifier.width(5.dp)) // Jarak lebih kecil untuk elemen tanggal
                        Text(
                            text = klien.idKlien,
                            style = MaterialTheme.typography.bodySmall, // Gaya teks lebih kecil
                            color = Color.White
                        )
                    }
                }

                Divider(
                    color = colorResource(R.color.yellow),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Call, // Ikon lokasi
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kontak: ${klien.kontakKlien}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowForward, // Ikon panah ke kanan
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Posisikan di pojok kanan bawah
                    .padding(15.dp) // Beri jarak dari tepi
                    .size(25.dp) // Ukuran ikon
            )
        }
    }
}

