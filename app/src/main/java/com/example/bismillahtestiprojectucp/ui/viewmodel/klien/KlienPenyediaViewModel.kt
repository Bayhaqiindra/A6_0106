package com.example.bismillahtestiprojectucp.ui.viewmodel.klien

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bismillahtestiprojectucp.EventApplications
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.DetailAcaraViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.UpdateAcaraViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.acara.acaraApp

object KlienPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeKlienViewModel(acaraApp().container.klienRepository) }
        initializer { InsertKlienViewModel(acaraApp().container.klienRepository) }
        initializer { DetailKlienViewModel(createSavedStateHandle(), klen = acaraApp().container.klienRepository) }
        initializer { UpdateKlienViewModel(createSavedStateHandle(), klen = acaraApp().container.klienRepository) }
    }
}

fun CreationExtras.acaraApp(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)