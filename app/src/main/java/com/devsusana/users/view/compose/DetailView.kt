package com.devsusana.users.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import com.devsusana.users.data.model.userById.UserById
import com.devsusana.users.data.model.userById.UserDataById
import com.devsusana.users.data.model.userById.UserSupportById

@ExperimentalCoilApi
@Composable
fun DetailView(detail: UserById) {

    ConstraintLayout() {

        val viewCard = createRef()

        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(viewCard) {
                    top.linkTo(parent.top, margin = 16.dp)
                },
            elevation = 10.dp,
            shape = RoundedCornerShape(corner = CornerSize(10.dp))
        ) {
            ConstraintLayout() {
                val columnConstraint = createRef()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(columnConstraint) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {

                    ConstraintLayout() {
                        val imageConstraint = createRef()
                        UserImage(imageUrl = detail.data.avatar, modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp
                                )
                            )
                            .height(440.dp)
                            .constrainAs(imageConstraint) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }

                    Text(
                        text = detail.data.first_name,
                        Modifier
                            .padding(15.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.h6
                    )
                    Row {
                        Text(
                            text = "Apellidos ->",
                            Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                        Text(
                            text = detail.data.last_name,
                            Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }
                    Row {
                        Text(
                            text = "Email ->",
                            Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                        Text(
                            text = detail.data.email,
                            Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailViewPreview() {
    val user = UserById(
        UserDataById(
            id = 2,
            email = "janet.weaver@reqres.in",
            first_name = "Janet",
            last_name = "Weaver",
            avatar = "https://reqres.in/img/faces/2-image.jpg"
        ),
        UserSupportById(
            text = "To keep ReqRes free, contributions towards server costs are appreciated!",
            url = "https://reqres.in/#support-heading"
        )
    )
    DetailView(detail = user)
}