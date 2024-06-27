package com.devsusana.users.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.devsusana.users.data.model.listuser.Data

@ExperimentalCoilApi
@Composable
fun UserImage(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(
            imageUrl
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )

}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun CharacterImagePreview(){

    val user = Data(
        id = 2,
        email = "janet.weaver@reqres.in",
        first_name = "Janet",
        last_name = "Weaver",
        avatar = "https://reqres.in/img/faces/2-image.jpg"
    )

    UserImage(
        imageUrl = user.avatar, modifier = Modifier
            .padding(4.dp)
            .height(140.dp)
            .width(140.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp))))
}