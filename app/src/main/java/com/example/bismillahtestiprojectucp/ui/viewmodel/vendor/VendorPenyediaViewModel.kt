package com.example.bismillahtestiprojectucp.ui.viewmodel.vendor

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

object VendorPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeVendorViewModel(acaraApp().container.vendorRepository) }
        initializer { InsertVendorViewModel(acaraApp().container.vendorRepository) }
        initializer { DetailVendorViewModel(createSavedStateHandle(), vendr = acaraApp().container.vendorRepository) }
        initializer { UpdateVendorViewModel(createSavedStateHandle(), vendr = acaraApp().container.vendorRepository) }
    }
}

fun CreationExtras.acaraApp(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)