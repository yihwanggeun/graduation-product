package edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.kaist.cs.teamfinder.R


@Composable
fun WelcomeScreen(onContinueClick: () -> Unit) {

    Surface {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = "TeamFinder",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(end = 32.dp)
                            .padding(top = 51.dp)
                            .shadow(elevation = 15.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp), horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login1),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(0.dp)
                        .width(280.dp)
                        .height(270.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(modifier = Modifier.padding(start = 16.dp, top = 40.dp)) {
                    val text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Find Your\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFCA34D),
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Project Team\n")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Here!")
                        }
                    }

                    Text(
                        text = text,
                        lineHeight = 38.sp,
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.dmsansbold))
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Explore all the most exciting projects based\non your interest and study major.",
                    fontSize = 15.sp,
                    color = Color(0xFF524B6B),
                    lineHeight = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
//            val coroutineScope = rememberCoroutineScope()
                IconButton(onClick = onContinueClick) {
                    Image(
                        painter = painterResource(id = R.drawable.login2),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(0.dp)
                            .width(50.dp)
                            .height(50.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onContinueClick = { }
    )
}