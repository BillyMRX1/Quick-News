package com.mrx.quicknews.ui.page

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mrx.quicknews.ui.composable.ErrorComponent
import com.mrx.quicknews.ui.composable.LoadingIndicator
import com.mrx.quicknews.ui.composable.NewsList
import com.mrx.quicknews.ui.composable.RequestLocation
import com.mrx.quicknews.viewmodel.HomeState
import com.mrx.quicknews.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalPermissionsApi::class)
@Composable
fun LocalPage(navController: NavHostController) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.getLocalTopHeadline()
        } else {
            viewModel.permissionDenied()
        }
    }

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState.status.isGranted) {
            val newsList = (state as HomeState.TopHeadline).articles
            if (newsList.isEmpty()) {
                viewModel.getLocalTopHeadline()
            }
        } else {
            viewModel.permissionDenied()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is HomeState.Loading -> {
                    LoadingIndicator()
                }

                is HomeState.TopHeadline -> {
                    val newsList = (state as HomeState.TopHeadline).articles
                    NewsList(
                        newsList = newsList,
                        navController = navController
                    )
                }

                is HomeState.Error -> {
                    val errorMessage = (state as HomeState.Error).errorMessage
                    ErrorComponent(errorMessage)
                }

                is HomeState.PermissionDenied -> {
                    RequestLocation {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                }
            }
        }
    }
}