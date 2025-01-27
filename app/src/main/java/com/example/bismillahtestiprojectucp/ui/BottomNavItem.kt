package com.example.bismillahtestiprojectucp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val icon: ImageVector,
    val route: String,) {
    object Acara : BottomNavItem(Icons.Default.Home, "acara")
    object Lokasi : BottomNavItem(Icons.Default.LocationOn, "lokasi")
    object Vendor : BottomNavItem(Icons.Default.Notifications, "vendor")
    object Klien : BottomNavItem(Icons.Default.Person, "klien")
}
