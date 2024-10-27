package edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.kaist.cs.teamfinder.Globals
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.AddDescriptionScreen
import edu.kaist.cs.teamfinder.ui.theme.TeamFinderTheme

@Composable
fun AddScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController, "AddScreen") {
        composable("AddScreen") { it ->
            it.savedStateHandle
            AddScreenContent(navController)
        }
        composable("AddFrontScreen") {
            AddFrontScreen(navController)
        }
        composable("AddBackScreen"){
            AddBackScreen(navController)
        }
        composable("AddDescriptionScreen"){
            AddDescriptionScreen()
        }
    }

}

@Composable
fun AddScreenContent(navController: NavController){
    //val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()){

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Web",
                modifier = Modifier
                    .size(24.dp)// 이미지 크기를 조절하십시오.
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Add a project",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF150B3D),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(70.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .height(IntrinsicSize.Min)
            ) {
                Row(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(), verticalAlignment = Alignment.CenterVertically){


                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Web Project",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.addbutton),
                        contentDescription = "Web",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                Globals.project_type = "Web Project"
                                navController.navigate("AddFrontScreen")

                            } // 이미지 크기를 조절하십시오.

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(70.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .height(IntrinsicSize.Min)
            ) {
                Row(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(), verticalAlignment = Alignment.CenterVertically){

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "App Project",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.addbutton),
                        contentDescription = "Web",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                Globals.project_type = "App Project"
                                navController.navigate("AddFrontScreen")

                            } // 이미지 크기를 조절하십시오.

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(70.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .height(IntrinsicSize.Min)
            ) {
                Row(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Software Project",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.addbutton),
                        contentDescription = "Web",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                Globals.project_type = "Software Project"
                                navController.navigate("AddFrontScreen")

                            } // 이미지 크기를 조절하십시오.

                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun AddScreenPreview(){
    TeamFinderTheme {
        AddScreen()
    }
}
