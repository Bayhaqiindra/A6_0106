package com.example.bismillahtestiprojectucp.ui.view.halamanhome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi

object DestinasiDashboard : DestinasiNavigasi {
    override val route = "dashboard"
    override val titleRes = "Dashboard"
}

@Composable
fun HalamanUtamaView(
    onAcaraClick: () -> Unit,
    onLokasiClick: () -> Unit,
    onVendorClick: () -> Unit,
    onKlienClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(
                id = R.color.primary
            ))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
        }

        // Konten utama di bawah lengkungan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)) // Membentuk tampilan seperti "card"
                .background(Color.White) // Latar belakang putih untuk konten
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Grid menu pilihan (2 kolom)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Menentukan jumlah kolom
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Menu Acara
                    item {
                        MenuCard(
                            title = "Acara",
                            iconRes = R.drawable.logo, // Ganti dengan resource icon acara
                            onClick = onAcaraClick
                        )
                    }

                    // Menu Klien
                    item {
                        MenuCard(
                            title = "Klien",
                            iconRes = R.drawable.logo, // Ganti dengan resource icon klien
                            onClick = onKlienClick
                        )
                    }

                    // Menu Lokasi
                    item {
                        MenuCard(
                            title = "Lokasi",
                            iconRes = R.drawable.logo, // Ganti dengan resource icon lokasi
                            onClick = onLokasiClick
                        )
                    }

                    // Menu Vendor
                    item {
                        MenuCard(
                            title = "Vendor",
                            iconRes = R.drawable.logo, // Ganti dengan resource icon vendor
                            onClick = onVendorClick
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MenuCard(
    title: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f) // Membuat card berbentuk persegi
            .clip(RoundedCornerShape(16.dp)) // Sedikit lengkungan
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD4AF37)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Gambar/Icon untuk menu
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = Color(0xFFE3F2FD), // Background icon
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Text menu
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
