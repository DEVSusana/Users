package com.devsusana.users.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.devsusana.users.R
import com.devsusana.users.data.model.listuser.UserDataList

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ListItem(
    navController: NavController,
    detail: UserDataList
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Surface(color = colorResource(R.color.lightBlueGray)) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("details/${detail.id}") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserImage(imageUrl = detail.avatar, modifier = Modifier
                    .padding(4.dp)
                    .height(140.dp)
                    .width(140.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(10.dp))))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = detail.first_name, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    val user = UserDataList(
        id = 2,
        email = "janet.weaver@reqres.in",
        first_name = "Janet",
        last_name = "Weaver",
        avatar = "https://reqres.in/img/faces/2-image.jpg"
    )
    ListItem(
        navController = rememberNavController(),
        detail = user
    )
}