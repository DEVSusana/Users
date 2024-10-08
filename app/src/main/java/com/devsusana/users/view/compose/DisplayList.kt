package com.devsusana.users.view.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.devsusana.users.data.model.listuser.UserDataList
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoilApi
@Composable
fun DisplayList(
    navController: NavController,
    resultItems: LazyPagingItems<UserDataList>,
    modifier: Modifier = Modifier
) {
    Surface(color = Color.LightGray) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                count = resultItems.itemCount
            ) { index ->
                val item = resultItems[index]
                if (item != null) {
                    ListItem(navController = navController, detail = item)
                }
            }
        }
    }
    resultItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                //You can add modifier to manage load state when first time response page is loading
                ShowProgressBar()
            }

            loadState.append is LoadState.Loading -> {
                //You can add modifier to manage load state when next response page is loading
                ShowProgressBar()
            }

            loadState.append is LoadState.Error -> {
                //You can use modifier to show error message
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DisplayListPreview() {
    val user = UserDataList(
        id = 2,
        email = "janet.weaver@reqres.in",
        first_name = "Janet",
        last_name = "Weaver",
        avatar = "https://reqres.in/img/faces/2-image.jpg"
    )
    DisplayList(
        navController = rememberNavController(),
        resultItems = flowOf(PagingData.from(listOf(user))).collectAsLazyPagingItems()
    )
}