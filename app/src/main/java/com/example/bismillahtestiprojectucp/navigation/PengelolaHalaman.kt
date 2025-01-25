package com.example.bismillahtestiprojectucp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bismillahtestiprojectucp.ui.view.acara.AcaraHomeScreen
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraHome
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraInsert
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiDetailAcara
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiUpdateAcara
import com.example.bismillahtestiprojectucp.ui.view.acara.DetailAcaraView
import com.example.bismillahtestiprojectucp.ui.view.acara.InsertAcaraScreen
import com.example.bismillahtestiprojectucp.ui.view.acara.UpdateAcaraView
import com.example.bismillahtestiprojectucp.ui.view.halamanhome.DestinasiDashboard
import com.example.bismillahtestiprojectucp.ui.view.halamanhome.HalamanUtamaView
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiDetailKlien
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiKlienHome
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiKlienInsert
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiUpdateKlien
import com.example.bismillahtestiprojectucp.ui.view.klien.DetailKlienView
import com.example.bismillahtestiprojectucp.ui.view.klien.InsertKlienScreen
import com.example.bismillahtestiprojectucp.ui.view.klien.KlienHomeScreen
import com.example.bismillahtestiprojectucp.ui.view.klien.UpdateKlienView
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiDetailLokasi
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiLokasiHome
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiLokasiInsert
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiUpdateLokasi
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DetailLokasiView
import com.example.bismillahtestiprojectucp.ui.view.lokasi.InsertLokasiScreen
import com.example.bismillahtestiprojectucp.ui.view.lokasi.LokasiHomeScreen
import com.example.bismillahtestiprojectucp.ui.view.lokasi.UpdateLokasiView
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiDetailVendor
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiUpdateVendor
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiVendorHome
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiVendorInsert
import com.example.bismillahtestiprojectucp.ui.view.vendor.DetailVendorView
import com.example.bismillahtestiprojectucp.ui.view.vendor.InsertVendorScreen
import com.example.bismillahtestiprojectucp.ui.view.vendor.UpdateVendorView
import com.example.bismillahtestiprojectucp.ui.view.vendor.VendorHomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = Modifier
    ) {
        //DASHBOARD
        composable(
            route = DestinasiDashboard.route
        ) {
            HalamanUtamaView(
                onAcaraClick = { navController.navigate(DestinasiAcaraHome.route) },
                onLokasiClick = { navController.navigate(DestinasiLokasiHome.route) },
                onVendorClick = { navController.navigate(DestinasiVendorHome.route) },
                onKlienClick = { navController.navigate(DestinasiKlienHome.route) }
            )
        }

        //DESTINASI ACARA
        composable(
            route = DestinasiAcaraHome.route
        ) {
            AcaraHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiAcaraInsert.route) },
                onDetailClick = {idAcara ->
                    navController.navigate("${DestinasiDetailAcara.route}/$idAcara")
                    println(idAcara)
                }
            )
        }

        composable(
            route = DestinasiAcaraInsert.route
        ) {
            InsertAcaraScreen(
                navigateBack = {
                    navController.navigate(DestinasiAcaraHome.route) {
                        popUpTo(DestinasiAcaraHome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailAcara.routeWithArgs,
           arguments = listOf(navArgument(DestinasiDetailAcara.idAcara){
                type = NavType.StringType
           }
           )
        )
        { backStackEntry ->
            val idAcara = backStackEntry.arguments?.getString(DestinasiDetailAcara.idAcara)
           idAcara?.let {
                DetailAcaraView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { idAcara ->
                        navController.navigate("${DestinasiUpdateAcara.route}/$idAcara")
                    }
                )
           }
        }

        composable(
            route = DestinasiUpdateAcara.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateAcara.idAcara){
                type = NavType.StringType
            })
        ) {
            UpdateAcaraView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateAcara.route
                    ) {
                        popUpTo(DestinasiAcaraHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        //DESTINASI LOKASI
        composable(
            route = DestinasiLokasiHome.route
        ) {
            LokasiHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiLokasiInsert.route) },
                onDetailClick = {idLokasi ->
                    navController.navigate("${DestinasiDetailLokasi.route}/$idLokasi")
                }
            )
        }

        composable(
            route = DestinasiDetailLokasi.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailLokasi.idLokasi){
                type = NavType.StringType
            }
            )
        )
        { backStackEntry ->
            val idLokasi = backStackEntry.arguments?.getString(DestinasiDetailLokasi.idLokasi)
            idLokasi?.let {
                DetailLokasiView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { idLokasi ->
                        navController.navigate("${DestinasiUpdateLokasi.route}/$idLokasi")
                    }
                )
            }
        }

        composable(
            route = DestinasiLokasiInsert.route
        ) {
            InsertLokasiScreen(
                navigateBack = {
                    navController.navigate(DestinasiLokasiHome.route) {
                        popUpTo(DestinasiLokasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiUpdateLokasi.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateLokasi.idLokasi){
                type = NavType.StringType
            })
        ) {
            UpdateLokasiView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateLokasi.route
                    ) {
                        popUpTo(DestinasiLokasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        //DESTINASI KLIEN
        composable(
            route = DestinasiKlienHome.route
        ) {
            KlienHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiKlienInsert.route) },
                onDetailClick = {idKlien ->
                    navController.navigate("${DestinasiDetailKlien.route}/$idKlien")
                    println(idKlien)
                }
            )
        }

        composable(
            route = DestinasiDetailKlien.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailKlien.idKlien){
                type = NavType.StringType
            }
            )
        )
        { backStackEntry ->
            val idKlien = backStackEntry.arguments?.getString(DestinasiDetailKlien.idKlien)
            idKlien?.let {
                DetailKlienView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { idKlien ->
                        navController.navigate("${DestinasiUpdateKlien.route}/$idKlien")
                    }
                )
            }
        }

        composable(
            route = DestinasiKlienInsert.route
        ) {
            InsertKlienScreen(
                navigateBack = {
                    navController.navigate(DestinasiKlienHome.route) {
                        popUpTo(DestinasiKlienHome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiUpdateKlien.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateKlien.idKlien){
                type = NavType.StringType
            })
        ) {
            UpdateKlienView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateKlien.route
                    ) {
                        popUpTo(DestinasiKlienHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        //DESTINASI VENDOR
        composable(
            route = DestinasiVendorHome.route
        ) {
            VendorHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiVendorInsert.route) },
                onDetailClick = {idVendor ->
                    navController.navigate("${DestinasiDetailVendor.route}/$idVendor")
                }
            )
        }

        composable(
            route = DestinasiDetailVendor.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailVendor.idVendor){
                type = NavType.StringType
            }
            )
        )
        { backStackEntry ->
            val idVendor = backStackEntry.arguments?.getString(DestinasiDetailVendor.idVendor)
            idVendor?.let {
                DetailVendorView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { idVendor ->
                        navController.navigate("${DestinasiUpdateVendor.route}/$idVendor")
                    }
                )
            }
        }

        composable(
            route = DestinasiVendorInsert.route
        ) {
            InsertVendorScreen(
                navigateBack = {
                    navController.navigate(DestinasiVendorHome.route) {
                        popUpTo(DestinasiVendorHome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiUpdateVendor.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateVendor.idVendor){
                type = NavType.StringType
            })
        ) {
            UpdateVendorView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateVendor.route
                    ) {
                        popUpTo(DestinasiVendorHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}