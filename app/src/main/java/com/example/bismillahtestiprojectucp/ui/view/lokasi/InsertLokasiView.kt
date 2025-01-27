package com.example.bismillahtestiprojectucp.ui.view.lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.InsertLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiLokasiInsert : DestinasiNavigasi {
    override val route = "insert_lokasi"
    override val titleRes = "Masukan Data Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertLokasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Masukan Data Lokasi",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 25.sp
                ),
                color = Color.White,
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(110.dp),
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
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                EntryBodyLokasi(
                    insertLokasiUiState = viewModel.uilokasiState,
                    onLokasiValueChange = viewModel::updateInsertLokasiState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertLokasi()
                            navigateBack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EntryBodyLokasi(
    insertLokasiUiState: InsertLokasiUiState,
    onLokasiValueChange: (InsertLokasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputLokasi(
            insertLokasiUiEvent = insertLokasiUiState.insertUiEvent,
            onValueChange = onLokasiValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(), // Menambahkan padding untuk jarak dari tepi layar
            horizontalArrangement = Arrangement.End // Menempatkan tombol di kanan
        ) {
            Button(
                onClick = onSaveClick,
                shape = RoundedCornerShape(16.dp), // Membuat tombol lebih kecil dengan sudut membulat
                modifier = Modifier
                    .height(40.dp), // Mengatur tinggi tombol lebih kecil
                enabled = insertLokasiUiState.insertUiEvent.idLokasi.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary), // Warna latar tombol hijau
                    contentColor = Color.White, // Warna teks atau konten tombol putih
                    disabledContainerColor = Color.Gray, // Warna tombol saat tidak aktif
                    disabledContentColor = Color.LightGray // Warna teks saat tombol tidak aktif
                )
            ) {
                if (insertLokasiUiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White // Warna indikator loading menjadi putih
                    )
                } else {
                    Text(text = "Save")
                }
            }
        }

    }
}

@Composable
fun FormInputLokasi(
    insertLokasiUiEvent: InsertLokasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertLokasiUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertLokasiUiEvent.idLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(idLokasi = it)) },
            label = { Text("ID Lokasi", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White, // Warna bingkai saat fokus
                unfocusedBorderColor = Color.White // Warna bingkai saat tidak fokus
            )
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.namaLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(namaLokasi = it)) },
            label = { Text("Nama Lokasi", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White, // Warna bingkai saat fokus
                unfocusedBorderColor = Color.White // Warna bingkai saat tidak fokus
            )
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.alamatLokasi,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(alamatLokasi = it)) },
            label = { Text("Alamat Lokasi", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White, // Warna bingkai saat fokus
                unfocusedBorderColor = Color.White // Warna bingkai saat tidak fokus
            )// Membuat TextField bulat
        )

        OutlinedTextField(
            value = insertLokasiUiEvent.kapasitas,
            onValueChange = { onValueChange(insertLokasiUiEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(16.dp), // Membuat TextField bulat
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White, // Warna bingkai saat fokus
                unfocusedBorderColor = Color.White // Warna bingkai saat tidak fokus
            )
        )
    }
}

