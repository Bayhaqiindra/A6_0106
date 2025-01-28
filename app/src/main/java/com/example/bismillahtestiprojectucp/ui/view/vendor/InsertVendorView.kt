package com.example.bismillahtestiprojectucp.ui.view.vendor

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
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.InsertVendorViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiVendorInsert : DestinasiNavigasi {
    override val route = "insert_vendor"
    override val titleRes = "Masukan Data Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVendorScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertVendorViewModel = viewModel(factory = VendorPenyediaViewModel.Factory),
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
                text = "Masukan Data Vendor",
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
                EntryBodyVendor(
                    insertVendorUiState = viewModel.uiVendorState,
                    onVendorValueChange = viewModel::updateInsertVendorState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertVendor()
                            navigateBack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EntryBodyVendor(
    insertVendorUiState: InsertVendorUiState,
    onVendorValueChange: (InsertVendorUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputVendor(
            insertVendorUiEvent = insertVendorUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End // Tombol berada di kanan
        ) {
            Button(
                onClick = onSaveClick,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.height(40.dp),
                enabled = insertVendorUiState.insertUiEvent.idVendor.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.LightGray
                )
            ) {
                if (insertVendorUiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
fun FormInputVendor(
    insertVendorUiEvent: InsertVendorUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertVendorUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertVendorUiEvent.idVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(idVendor = it)) },
            label = { Text("ID Vendor", color = Color.White) },
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
            value = insertVendorUiEvent.namaVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(namaVendor = it)) },
            label = { Text("Nama Vendor", color = Color.White) },
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
            value = insertVendorUiEvent.jenisVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(jenisVendor = it)) },
            label = { Text("Jenis Vendor", color = Color.White) },
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
            value = insertVendorUiEvent.kontakVendor,
            onValueChange = { onValueChange(insertVendorUiEvent.copy(kontakVendor = it)) },
            label = { Text("Kontak Vendor", color = Color.White) },
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

