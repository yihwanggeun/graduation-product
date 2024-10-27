package edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.kaist.cs.teamfinder.CreateAccount
import edu.kaist.cs.teamfinder.Engineer
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.ui.theme.TeamFinderTheme

@Composable
fun TopRateEngineer(engineerList: List<Engineer>, navController: NavController,onDevelopClick: () -> Unit) {
    LazyRow (modifier = Modifier.padding(start = 8.dp)){
        items(engineerList) { engineer ->
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource(id = engineer.imageResourceId),
                    contentDescription = engineer.name,
                    modifier = Modifier
                        .size(100.dp) // 이미지 크기를 조절하십시오.
                        .padding(4.dp)
                        .clip(CircleShape).clickable(){onDevelopClick()}

                )
                Text(
                    text = engineer.name,
                    //modifier = Modifier.padding(top = (-4).dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF0D0140),
                        textAlign = TextAlign.Center,
                    ),
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "google",
                        modifier = Modifier
                            .size(8.dp).padding(end = 2.dp)

                    )
                    Text(
                        text = "Web Engineer",
                        style = TextStyle(
                            fontSize = 6.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF0D0140),
                            textAlign = TextAlign.Center,
                        ),
                    )
                }



            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EngineerView() {
    TeamFinderTheme {

        //TopRateEngineer(engineerList: List< Engineer >)
        val engineerList = listOf(
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample),
            Engineer("이황근", R.drawable.sample)
            // 기타 기술자 추가
        )
        //TopRateEngineer(engineerList)
    }
}