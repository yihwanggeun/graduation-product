package edu.kaist.cs.teamfinder.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import edu.kaist.cs.teamfinder.Project
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.WebTypeScreen
import edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.getImageResourceId

@Composable
fun AppTypeScreen(navController: NavController) {

        val specializationList = listOf(
            "FrontEnd" to listOf("Android", "React Native", "Flutter", "Swift"),
            "BackEnd" to listOf("Django", "Node.js", "MySQL", "Oracle")
        )
        val selectedBoxIndexList = MutableList(specializationList.size) { -1 }
        val projectList = remember { mutableStateListOf<Project>() }
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).height(5000.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Specialization",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.dmsansbold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF150A33),
                    )
                )

                specializationList.forEachIndexed { categoryIndex, (category, items) ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = category,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150A33),
                        )
                    )

                    val rows = items.chunked(2) // 항목을 2개씩 묶어서 각 Row에 배치

                    rows.forEach { rowItems ->
                        Row {
                            rowItems.forEach { item ->
                                val index = categoryIndex * items.size + items.indexOf(item)

                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .weight(1f)
                                        .height(180.dp)
                                        .background(
                                            color = if (selectedBoxIndexList[categoryIndex] == items.indexOf(item)) {
                                                Color(0xFFC4C4C4)
                                            } else {
                                                Color(0xFFFFFFFF)
                                            },
                                            shape = RoundedCornerShape(size = 16.dp)
                                        )
                                        .clickable {
                                            selectedBoxIndexList[categoryIndex] = items.indexOf(item)
                                            var frame = "React"
                                            println(projectList)
                                            if(categoryIndex==0){
                                                if(index==0){
                                                    frame = "Android"
                                                }
                                                else if(index == 1){
                                                    frame = "React Native"
                                                }
                                                else if(index == 2){
                                                    frame = "Swift"
                                                }
                                                else{
                                                    frame = "Flutter"
                                                }
                                            }
                                            else {
                                                if(index==0){
                                                    frame = "Django"
                                                }
                                                else if(index == 1){
                                                    frame = "Node.js"
                                                }
                                                else if(index == 2){
                                                    frame = "MySQL"
                                                }
                                                else{
                                                    frame = "Oracle"
                                                }
                                            }
                                            Log.d("frame",frame)
                                            edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.gettypeproject(projectList,context,frame)
                                            navController.navigate("typeProject/${frame}")
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(top = 32.dp)
                                                .size(70.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFF9F9F9))
                                        ) {
                                            //Log.i("TAG",getImageResourceId(item).toString())

                                            Image(
                                                painter = painterResource(id = getImageResourceId2(item)),
                                                contentDescription = item,
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(CircleShape)
                                                    .align(Alignment.Center)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = item,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.dmsans)),
                                                fontWeight = FontWeight(700),
                                                color = Color(0xFF150B3D),
                                                textAlign = TextAlign.Center
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

}

@Preview
@Composable
fun AppTypeScreenPreview() {
    AppTypeScreen(navController = rememberNavController())
}

@Composable
fun getImageResourceId2(item: String): Int {
    //println(item)
    return when (item) {
        "React Native" -> R.drawable.react
        "Android" -> R.drawable.android
        "Swift" -> R.drawable.swift
        "Flutter" -> R.drawable.flutter
        "Django" -> R.drawable.django
        "Node.js" -> R.drawable.nodejs
        "MySQL" -> R.drawable.mysql
        "Oracle" -> R.drawable.oracle
        else -> R.drawable.flutter // 기본 이미지
    }
}