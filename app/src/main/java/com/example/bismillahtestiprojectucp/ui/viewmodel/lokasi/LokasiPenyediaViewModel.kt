package com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bismillahtestiprojectucp.EventApplications
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.acaraApp
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.DetailKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.UpdateKlienViewModel

object LokasiPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeLokasiViewModel(acaraApp().container.lokasiRepository) }
        initializer { InsertLokasiViewModel(acaraApp().container.lokasiRepository) }
        initializer { DetailLokasiViewModel(createSavedStateHandle(), lks = acaraApp().container.lokasiRepository) }
        initializer { UpdateLokasiViewModel(createSavedStateHandle(), lks = acaraApp().container.lokasiRepository) }
    }
}

fun CreationExtras.acaraApp(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)