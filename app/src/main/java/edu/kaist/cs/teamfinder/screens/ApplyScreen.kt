package edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.kaist.cs.teamfinder.R

@Composable
fun ApplyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Box(
                modifier = Modifier
                        .size(80.dp) // Adjust the size of your image
                        .clip(CircleShape)
                        .background(Color(0xFFD6CDFE)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "apple",
                    modifier = Modifier
                            .size(24.dp) // Adjust the size of your image
                            .clip(CircleShape)
                )
            }
        }



    }
}

@Preview
@Composable
fun ApplyScreenPreview() {
    ApplyScreen()
}
