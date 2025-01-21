package com.example.bismillahtestiprojectucp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bismillahtestiprojectucp.ui.view.acara.AcaraHomeScreen
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraHome
import com.example.bismillahtestiprojectucp.ui.view.halamanhome.DestinasiDashboard
import com.example.bismillahtestiprojectucp.ui.view.halamanhome.HalamanUtamaView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = Modifier
    ) {
        composable(
            route = DestinasiDashboard.route
        ) {
            HalamanUtamaView(
                onAcaraClick = { navController.navigate(DestinasiAcaraHome.route) },
                onLokasiClick = { /* Add logic */ },
                onVendorClick = { /* Add logic */ },
                onKlienClick = { /* Add logic */ }
            )
        }
        composable(
            route = DestinasiAcaraHome.route
        ) {
            AcaraHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiDashboard.route) }
            )
        }
    }
}
