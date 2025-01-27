package com.example.bismillahtestiprojectucp.ui.view.acara

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.AcaraPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.HomeAcaraUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.HomeKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.KlienUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.HomeLokasiViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi.LokasiUiState
import com.example.bismillahtestiprojectucp.ui.widget.DynamicSelectedTextField
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiEvent
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiState
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraViewModel
import kotlinx.coroutines.launch

object DestinasiAcaraInsert : DestinasiNavigasi {
    override val route = "insert_acara"
    override val titleRes = "Masukan Data Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAcaraViewModel = viewModel(factory = AcaraPenyediaViewModel.Factory),
    viewModelLokasi: HomeLokasiViewModel = viewModel(factory = LokasiPenyediaViewModel.Factory),
    viewModelKlien: HomeKlienViewModel = viewModel(factory = KlienPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val klienUiState = viewModelKlien.klienUiState
    val lokasiUiState = viewModelLokasi.lokasiUiState

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
                text = "Masukan Data Acara",
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
                EntryBodyAcara(
                    insertAcaraUiState = viewModel.uiacaraState,
                    onAcaraValueChange = viewModel::updateInsertAcaraState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertAcara()
                            navigateBack()
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun EntryBodyAcara(
    insertAcaraUiState: InsertAcaraUiState,
    onAcaraValueChange: (InsertAcaraUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAcara(
            insertAcaraUiEvent = insertAcaraUiState.insertUiEvent,
            onValueChange = onAcaraValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelLokasiViewModel = viewModel(),
            viewModelKlienViewModel = viewModel()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onSaveClick,
                shape = RoundedCornerShape(16.dp), // Membuat tombol lebih kecil dengan sudut membulat
                modifier = Modifier
                    .height(40.dp),
                enabled = insertAcaraUiState.insertUiEvent.idKlien.isNotEmpty()
                        && insertAcaraUiState.insertUiEvent.idLokasi.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.LightGray
                )// Validasi form
            ) {
                if (insertAcaraUiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    ) // Loading indicator
                } else {
                    Text(text = "Simpan")
                }
            }
        }
    }
}

@Composable
fun FormInputAcara(
    insertAcaraUiEvent: InsertAcaraUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAcaraUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelKlienViewModel: HomeKlienViewModel,
    viewModelLokasiViewModel: HomeLokasiViewModel// Menambahkan parameter untuk TanamanViewModel
) {
    // Ambil data tanaman dari HomeTanamanViewModel
    val lokasiUiState = viewModelLokasiViewModel.lokasiUiState
    val klienUiState = viewModelKlienViewModel.klienUiState

    // Menampilkan UI berdasarkan state
    when (klienUiState) {
        is KlienUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is KlienUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
        }

        is KlienUiState.Success -> {
            val klienList = klienUiState.klien // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = insertAcaraUiEvent.namaAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(namaAcara = it)) },
                    label = { Text("Nama Acara", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = insertAcaraUiEvent.deskripsiAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(deskripsiAcara = it)) },
                    label = { Text("Deskripsi Acara", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = insertAcaraUiEvent.tanggalMulai,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(tanggalMulai = it)) },
                    label = { Text("Tanggal Mulai", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = insertAcaraUiEvent.tanggalBerakhir,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(tanggalBerakhir = it)) },
                    label = { Text("Tanggal Berakhir", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )

                DynamicSelectedTextField(
                    selectedValue = insertAcaraUiEvent.idKlien.toString(), // Mengonversi idTanaman ke String
                    options = klienList.map { it.idKlien.toString() },
                    label = "Pilih ID Klien",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertAcaraUiEvent.copy(idKlien = selectedId))
                    }
                )

                OutlinedTextField(
                    value = insertAcaraUiEvent.statusAcara,
                    onValueChange = { onValueChange(insertAcaraUiEvent.copy(statusAcara = it)) },
                    label = { Text("Status Acara",  color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )
            }
        }
    }

    when (lokasiUiState) {
        is LokasiUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is LokasiUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
        }

        is LokasiUiState.Success -> {
            val lokasiList = lokasiUiState.lokasi // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DynamicSelectedTextField(
                    selectedValue = insertAcaraUiEvent.idLokasi.toString(), // Mengonversi idTanaman ke String
                    options = lokasiList.map { it.idLokasi.toString() }, // Mengonversi ID tanaman ke String
                    label = "Pilih ID Lokasi",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertAcaraUiEvent.copy(idLokasi = selectedId))
                    }
                )
            }
        }
    }
}