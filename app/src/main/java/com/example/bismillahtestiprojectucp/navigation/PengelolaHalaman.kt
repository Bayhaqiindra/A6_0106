package com.example.bismillahtestiprojectucp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.ui.BottomNavItem
import com.example.bismillahtestiprojectucp.ui.view.acara.AcaraHomeScreen
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraHome
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiAcaraInsert
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiDetailAcara
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiUpdateAcara
import com.example.bismillahtestiprojectucp.ui.view.acara.DetailAcaraView
import com.example.bismillahtestiprojectucp.ui.view.acara.InsertAcaraScreen
import com.example.bismillahtestiprojectucp.ui.view.acara.UpdateAcaraView
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        BottomNavItem.Acara,
        BottomNavItem.Vendor,
        BottomNavItem.Lokasi,
        BottomNavItem.Klien
    )
    Scaffold(
        bottomBar = {
            BottomNavigation (
                backgroundColor =  colorResource(R.color.primary),
                modifier = Modifier
                    .padding(10.dp)
                .clip(RoundedCornerShape(24.dp)) // Melengkungkan sudut
                .background(colorResource(R.color.primary)) // Warna latar belakang
                .shadow(8.dp, RoundedCornerShape(24.dp)),// Warna latar belakang hijau
            ){
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == item.route,
                        onClick = { navController.navigate(item.route) },
                        icon = { Icon(imageVector = item.icon,
                            contentDescription = "",
                            tint = Color.White) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Acara.route,
            modifier = Modifier
        ) {
            // Rute Acara
            composable(route = BottomNavItem.Acara.route) {
                AcaraHomeScreen(
                    navigateToItemEntry = { navController.navigate(DestinasiAcaraInsert.route) },
                    onDetailClick = { idAcara ->
                        navController.navigate("${DestinasiDetailAcara.route}/$idAcara")
                    }
                )
            }
            composable(route = DestinasiAcaraInsert.route) {
                InsertAcaraScreen(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = DestinasiDetailAcara.routeWithArgs,
                arguments = listOf(navArgument(DestinasiDetailAcara.idAcara) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val idAcara = backStackEntry.arguments?.getString(DestinasiDetailAcara.idAcara)
                idAcara?.let {
                    DetailAcaraView(
                        navigateBack = { navController.navigateUp() },
                        onEditClick = { navController.navigate("${DestinasiUpdateAcara.route}/$it") }
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

            // Rute Vendor
            composable(route = BottomNavItem.Vendor.route) {
                VendorHomeScreen(
                    navigateToItemEntry = { navController.navigate(DestinasiVendorInsert.route) },
                    onDetailClick = { idVendor ->
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
                        navController.popBackStack()
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

            // Rute Lokasi
            composable(route = BottomNavItem.Lokasi.route) {
                LokasiHomeScreen(
                    navigateToItemEntry = { navController.navigate(DestinasiLokasiInsert.route) },
                    onDetailClick = { idLokasi ->
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
                        navController.popBackStack()
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

            // Rute Klien
            composable(route = BottomNavItem.Klien.route) {
                KlienHomeScreen(
                    navigateToItemEntry = { navController.navigate(DestinasiKlienInsert.route) },
                    onDetailClick = { idKlien ->
                        navController.navigate("${DestinasiDetailKlien.route}/$idKlien")
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
                   navController.popBackStack()
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
        }
    }
}






















//@Composable
//fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
//    NavHost(
//        navController = navController,
//        startDestination = DestinasiAcaraHome.route,
//        modifier = Modifier
//    ) {
//        //DASHBOARD
//        composable(
//            route = DestinasiDashboard.route
//        ) {
//            HalamanUtamaView(
//                onAcaraClick = { navController.navigate(DestinasiAcaraHome.route) },
//                onLokasiClick = { navController.navigate(DestinasiLokasiHome.route) },
//                onVendorClick = { navController.navigate(DestinasiVendorHome.route) },
//                onKlienClick = { navController.navigate(DestinasiKlienHome.route) }
//            )
//        }

//        //DESTINASI ACARA
//        composable(
//            route = DestinasiAcaraHome.route
//        ) {
//            AcaraHomeScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiAcaraInsert.route) },
//                onDetailClick = {idAcara ->
//                    navController.navigate("${DestinasiDetailAcara.route}/$idAcara")
//                    println(idAcara)
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiAcaraInsert.route
//        ) {
//            InsertAcaraScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiAcaraHome.route) {
//                        popUpTo(DestinasiAcaraHome.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiDetailAcara.routeWithArgs,
//           arguments = listOf(navArgument(DestinasiDetailAcara.idAcara){
//                type = NavType.StringType
//           }
//           )
//        )
//        { backStackEntry ->
//            val idAcara = backStackEntry.arguments?.getString(DestinasiDetailAcara.idAcara)
//           idAcara?.let {
//                DetailAcaraView(
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    onEditClick = { idAcara ->
//                        navController.navigate("${DestinasiUpdateAcara.route}/$idAcara")
//                    }
//                )
//           }
//        }
//
//        composable(
//            route = DestinasiUpdateAcara.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiUpdateAcara.idAcara){
//                type = NavType.StringType
//            })
//        ) {
//            UpdateAcaraView(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateUp = {
//                    navController.navigate(
//                        DestinasiUpdateAcara.route
//                    ) {
//                        popUpTo(DestinasiAcaraHome.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
//
//
//        //DESTINASI LOKASI
//        composable(
//            route = DestinasiLokasiHome.route
//        ) {
//            LokasiHomeScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiLokasiInsert.route) },
//                onDetailClick = {idLokasi ->
//                    navController.navigate("${DestinasiDetailLokasi.route}/$idLokasi")
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiDetailLokasi.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiDetailLokasi.idLokasi){
//                type = NavType.StringType
//            }
//            )
//        )
//        { backStackEntry ->
//            val idLokasi = backStackEntry.arguments?.getString(DestinasiDetailLokasi.idLokasi)
//            idLokasi?.let {
//                DetailLokasiView(
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    onEditClick = { idLokasi ->
//                        navController.navigate("${DestinasiUpdateLokasi.route}/$idLokasi")
//                    }
//                )
//            }
//        }
//
//        composable(
//            route = DestinasiLokasiInsert.route
//        ) {
//            InsertLokasiScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiLokasiHome.route) {
//                        popUpTo(DestinasiLokasiHome.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiUpdateLokasi.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiUpdateLokasi.idLokasi){
//                type = NavType.StringType
//            })
//        ) {
//            UpdateLokasiView(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateUp = {
//                    navController.navigate(
//                        DestinasiUpdateLokasi.route
//                    ) {
//                        popUpTo(DestinasiLokasiHome.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
//
//        //DESTINASI KLIEN
//        composable(
//            route = DestinasiKlienHome.route
//        ) {
//            KlienHomeScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiKlienInsert.route) },
//                onDetailClick = {idKlien ->
//                    navController.navigate("${DestinasiDetailKlien.route}/$idKlien")
//                    println(idKlien)
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiDetailKlien.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiDetailKlien.idKlien){
//                type = NavType.StringType
//            }
//            )
//        )
//        { backStackEntry ->
//            val idKlien = backStackEntry.arguments?.getString(DestinasiDetailKlien.idKlien)
//            idKlien?.let {
//                DetailKlienView(
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    onEditClick = { idKlien ->
//                        navController.navigate("${DestinasiUpdateKlien.route}/$idKlien")
//                    }
//                )
//            }
//        }
//
//        composable(
//            route = DestinasiKlienInsert.route
//        ) {
//            InsertKlienScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiKlienHome.route) {
//                        popUpTo(DestinasiKlienHome.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiUpdateKlien.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiUpdateKlien.idKlien){
//                type = NavType.StringType
//            })
//        ) {
//            UpdateKlienView(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateUp = {
//                    navController.navigate(
//                        DestinasiUpdateKlien.route
//                    ) {
//                        popUpTo(DestinasiKlienHome.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
//
//        //DESTINASI VENDOR
//        composable(
//            route = DestinasiVendorHome.route
//        ) {
//            VendorHomeScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiVendorInsert.route) },
//                onDetailClick = {idVendor ->
//                    navController.navigate("${DestinasiDetailVendor.route}/$idVendor")
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiDetailVendor.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiDetailVendor.idVendor){
//                type = NavType.StringType
//            }
//            )
//        )
//        { backStackEntry ->
//            val idVendor = backStackEntry.arguments?.getString(DestinasiDetailVendor.idVendor)
//            idVendor?.let {
//                DetailVendorView(
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    onEditClick = { idVendor ->
//                        navController.navigate("${DestinasiUpdateVendor.route}/$idVendor")
//                    }
//                )
//            }
//        }
//
//        composable(
//            route = DestinasiVendorInsert.route
//        ) {
//            InsertVendorScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiVendorHome.route) {
//                        popUpTo(DestinasiVendorHome.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(
//            route = DestinasiUpdateVendor.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiUpdateVendor.idVendor){
//                type = NavType.StringType
//            })
//        ) {
//            UpdateVendorView(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateUp = {
//                    navController.navigate(
//                        DestinasiUpdateVendor.route
//                    ) {
//                        popUpTo(DestinasiVendorHome.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
//    }
//}