package edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.kaist.cs.teamfinder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostingScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF9F9F9))){
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(24.dp) // Adjust the size of your image
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.weight(1f))  // Add this line
                Text(
                    text = "Post",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFF9228),
                    )

                )
            }
            Spacer(modifier = Modifier.height(48.dp))  // Add this line

            Text(
                text = "Add Post",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF150B3D),
                )
            )

            Spacer(modifier = Modifier.height(48.dp))  // Add this line

            Row(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.sample),
                    contentDescription = "sample",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .size(36.dp) // 이미지 크기를 조절하십시오.
                        .clip(CircleShape)

                )
                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = "Michael Jackson",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "Web Developer",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF524B6B),
                        ),
                        modifier = Modifier.padding(start = 16.dp,top = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                }
            }

            Spacer(modifier = Modifier.height(24.dp))  // Add this line

            Text(
                text = "Post title",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var post_title by remember{
                mutableStateOf("")
            }
            TextField(
                value = post_title,
                onValueChange = { post_title = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))  // Add this line
            Text(
                text = "Description",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var post_description by remember{
                mutableStateOf("")
            }
            TextField(
                value = post_description,
                onValueChange = { post_description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

        }
    }
}

@Preview
@Composable
fun PostingScreenPreview() {
    PostingScreen()
}
