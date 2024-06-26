package com.devsusana.users.view.compose

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.devsusana.users.data.model.Data
import com.devsusana.users.data.utils.Resource
import com.devsusana.users.presentation.viewModel.ViewModel

@ExperimentalCoilApi
@Composable
fun NavigationComponent(
    navController: NavHostController,
    viewModel: ViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "list",
    ) {
        composable("list") {
            Scaffold(topBar = {
                TopAppBar(
                    backgroundColor = Color.DarkGray
                ) {
                    //TODO añadir un texto
                }
            }) { padding ->
                val resultItems: LazyPagingItems<Data> =
                    viewModel.resultUserList.collectAsLazyPagingItems()
                DisplayList(
                    navController = navController,
                    resultItems,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("id")?.let {
                LaunchedEffect(it) {
                    viewModel.getCharacterDetailResponse(it)
                }

            }
            val detail by viewModel.getUserDetail.observeAsState()
            when (detail) {
                is Resource.Success -> {
                    (detail as Resource.Success<Data>).data?.let { it1 ->
                        DetailView(
                            it1
                        )
                    }
                }

                is Resource.Loading -> {
                    ShowProgressBar()
                }

                is Resource.Error -> {
                    (detail as Resource.Error<*>).message?.let {
                        Toast.makeText(
                            LocalContext.current,
                            "An error occurred : $it",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.i("ERROR", it)
                    }
                }

                else -> {
                }
            }
            val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            DisposableEffect(backDispatcher) {
                onDispose {
                    viewModel.invalidateResultDataSource()
                }
            }
        }
    }

}