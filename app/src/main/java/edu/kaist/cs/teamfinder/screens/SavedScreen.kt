package edu.kaist.cs.teamfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.kaist.cs.teamfinder.Project
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.ui.theme.TeamFinderTheme

@Composable
fun SavedScreen() {
    val projectList = listOf(
        Project(
            "Schedule App Using Chat GPT",
            "이 프로젝트는 Chat GPT를 사용한 프로젝트입니다...",
            "4년제 대학교 졸업자 우대\nReact Native 개발 경험 1년 이상...",
            "App Project",
            "홍길동",
            "React Native",
            "Django",
            4,
            1
        ),
        Project(
            "AI Research Project",
            "AI 연구 프로젝트...",
            "AI 관련 학위 소지자 우대\nPython 경험 2년 이상...",
            "Research Project",
            "이몽룡",
            "Python",
            "Python",
            5,
            2
        ),
        Project(
            "E-Commerce Web App",
            "전자상거래 웹 앱 프로젝트...",
            "JavaScript, HTML, CSS 경험자 우대...",
            "Web App Project",
            "성춘향",
            "React",
            "Node.js",
            6,
            3
        ),
        Project(
            "Mobile Game Development",
            "모바일 게임 개발 프로젝트...",
            "Unity 사용 경험자 우대...",
            "Game Project",
            "김삿갓",
            "Unity",
            "C#",
            5,
            2
        ),
        Project(
            "Data Science Project",
            "데이터 과학 프로젝트...",
            "데이터 과학 관련 학위 소지자 우대\nPython, R 경험자 우대...",
            "Research Project",
            "박몽룡",
            "Python",
            "R",
            3,
            1
        ),
        Project(
            "Augmented Reality App",
            "증강현실 앱 개발 프로젝트...",
            "Unity, C# 경험자 우대...",
            "App Project",
            "김철수",
            "Unity",
            "C#",
            4,
            2
        ),
        Project(
            "Healthcare App",
            "헬스케어 앱 개발 프로젝트...",
            "React Native 경험자 우대...",
            "App Project",
            "이영희",
            "React Native",
            "Django",
            5,
            2
        ),
        Project(
            "Blockchain Project",
            "블록체인 프로젝트...",
            "블록체인 관련 경험자 우대...",
            "Blockchain Project",
            "최철호",
            "Ethereum",
            "Solidity",
            3,
            1
        ),
        Project(
            "Machine Learning Project",
            "머신러닝 프로젝트...",
            "머신러닝 관련 학위 소지자 우대\nPython, TensorFlow 경험자 우대...",
            "Research Project",
            "장보고",
            "Python",
            "TensorFlow",
            4,
            2
        ),
        Project(
            "IoT Development",
            "IoT 개발 프로젝트...",
            "IoT 개발 경험자 우대...",
            "IoT Project",
            "이이",
            "Python",
            "Node.js",
            6,
            3
        )
        // 기타 기술자 추가
    )
    TeamFinderTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (projectList.isEmpty()) {
                Text(text = "No Savings")
            } else {
                SavedProjectList(projectList)
            }
        }
    }
}

@Composable
fun SavedProjectList(savedProjectList: List<Project>) {
    LazyColumn(

    ) {
        items(savedProjectList) { savedProject ->
            SavedProjectCard(project = savedProject)
        }
    }
}

@Composable
fun SavedProjectCard(project: Project) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .width(400.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 6.dp)
            )
            .height(IntrinsicSize.Min),

        ) {
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp) // 이미지 크기를 조절하십시오.
                        .padding(start = 16.dp, top = 16.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF9F9F9)),
                    contentAlignment = Alignment.TopStart

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.react),
                        contentDescription = project.projectName,
                        modifier = Modifier
                            .size(48.dp) // 이미지 크기를 조절하십시오.
                            .clip(CircleShape)
                            .align(Alignment.Center)

                    )
                }
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.filledsave),
                        contentDescription = "save",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.projectName,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                    )
                    Text(
                        text = project.projectType,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF524B6B),
                        ),
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(start = 18.dp)) {
                val text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.dmsansbold))
                        )
                    ) {
                        append(project.current.toString() + "명 / ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFFCA34D),
                            fontFamily = FontFamily(Font(R.font.dmsansbold)),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(project.max.toString() + "명\n")
                    }
                }

                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.dmsansbold)),
                    modifier = Modifier.height(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        // 첫번째 Box
                        Row {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth() // 가용 공간을 차지하도록 설정
                                    .padding(bottom = 8.dp, start = 16.dp)
                                    .height(30.dp)
                                    .background(
                                        color = Color(0x1ACBC9D4),
                                        shape = RoundedCornerShape(size = 6.dp)
                                    ),
                                contentAlignment = Alignment.CenterStart // 왼쪽 정렬
                            ) {
                                Text(
                                    text = project.front + " Developer",
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.dmsans)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF524B6B),
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            // 두번째 Box
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth() // 가용 공간을 차지하도록 설정
                                    .height(30.dp)
                                    .padding(start = 8.dp)
                                    .background(
                                        color = Color(0x1ACBC9D4),
                                        shape = RoundedCornerShape(size = 6.dp)
                                    ),
                                contentAlignment = Alignment.CenterStart // 왼쪽 정렬
                            ) {
                                Text(
                                    text = project.back + " Developer",
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.dmsans)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF524B6B),
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                    // 'Apply' 버튼
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(30.dp)
                            .padding(end = 16.dp)
                            .background(
                                color = Color(0x1AFF6B2C),
                                shape = RoundedCornerShape(size = 6.dp)
                            ),
                        contentAlignment = Alignment.Center,

                        ) {
                        val text = buildAnnotatedString {
                            pushStringAnnotation(
                                tag = "Apply",
                                annotation = "Apply"
                            ) // Use a tag to identify the clickable text
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF524B6B),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Apply")
                            }
                            pop()
                        }
                        ClickableText(
                            text = text,
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.dmsans)),
                                fontWeight = FontWeight(400),
                            ),
                            modifier = Modifier.padding(4.dp),
                            onClick = { offset ->
                                text.getStringAnnotations(
                                    tag = "Apply",
                                    start = offset,
                                    end = offset
                                )
                                    .firstOrNull()?.let {
                                        println("Apply")
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SavedProjectCardPreview() {
    val project = Project(
        "Schedule App Using Chat GPT",
        "이 프로젝트는 Chat GPT를 사용한 프로젝트입니다...",
        "4년제 대학교 졸업자 우대\nReact Native 개발 경험 1년 이상...",
        "App Project",
        "홍길동",
        "React Native",
        "Django",
        4,
        1
    )
    SavedProjectCard(project)
}

@Preview
@Composable
fun SavedProjectListPreview() {
    val projectList = listOf(
        Project(
            "Schedule App Using Chat GPT",
            "이 프로젝트는 Chat GPT를 사용한 프로젝트입니다...",
            "4년제 대학교 졸업자 우대\nReact Native 개발 경험 1년 이상...",
            "App Project",
            "홍길동",
            "React Native",
            "Django",
            4,
            1
        ),
        Project(
            "AI Research Project",
            "AI 연구 프로젝트...",
            "AI 관련 학위 소지자 우대\nPython 경험 2년 이상...",
            "Research Project",
            "이몽룡",
            "Python",
            "Python",
            5,
            2
        ),
        Project(
            "E-Commerce Web App",
            "전자상거래 웹 앱 프로젝트...",
            "JavaScript, HTML, CSS 경험자 우대...",
            "Web App Project",
            "성춘향",
            "React",
            "Node.js",
            6,
            3
        ),
        Project(
            "Mobile Game Development",
            "모바일 게임 개발 프로젝트...",
            "Unity 사용 경험자 우대...",
            "Game Project",
            "김삿갓",
            "Unity",
            "C#",
            5,
            2
        ),
        Project(
            "Data Science Project",
            "데이터 과학 프로젝트...",
            "데이터 과학 관련 학위 소지자 우대\nPython, R 경험자 우대...",
            "Research Project",
            "박몽룡",
            "Python",
            "R",
            3,
            1
        ),
        Project(
            "Augmented Reality App",
            "증강현실 앱 개발 프로젝트...",
            "Unity, C# 경험자 우대...",
            "App Project",
            "김철수",
            "Unity",
            "C#",
            4,
            2
        ),
        Project(
            "Healthcare App",
            "헬스케어 앱 개발 프로젝트...",
            "React Native 경험자 우대...",
            "App Project",
            "이영희",
            "React Native",
            "Django",
            5,
            2
        ),
        Project(
            "Blockchain Project",
            "블록체인 프로젝트...",
            "블록체인 관련 경험자 우대...",
            "Blockchain Project",
            "최철호",
            "Ethereum",
            "Solidity",
            3,
            1
        ),
        Project(
            "Machine Learning Project",
            "머신러닝 프로젝트...",
            "머신러닝 관련 학위 소지자 우대\nPython, TensorFlow 경험자 우대...",
            "Research Project",
            "장보고",
            "Python",
            "TensorFlow",
            4,
            2
        ),
        Project(
            "IoT Development",
            "IoT 개발 프로젝트...",
            "IoT 개발 경험자 우대...",
            "IoT Project",
            "이이",
            "Python",
            "Node.js",
            6,
            3
        )
        // 기타 기술자 추가
    )
    SavedProjectList(projectList)
}

@Preview
@Composable
fun SavedScreenPreview() {
    val projectList = listOf(
        Project(
            "Schedule App Using Chat GPT",
            "이 프로젝트는 Chat GPT를 사용한 프로젝트입니다...",
            "4년제 대학교 졸업자 우대\nReact Native 개발 경험 1년 이상...",
            "App Project",
            "홍길동",
            "React Native",
            "Django",
            4,
            1
        ),
        Project(
            "AI Research Project",
            "AI 연구 프로젝트...",
            "AI 관련 학위 소지자 우대\nPython 경험 2년 이상...",
            "Research Project",
            "이몽룡",
            "Python",
            "Python",
            5,
            2
        ),
        Project(
            "E-Commerce Web App",
            "전자상거래 웹 앱 프로젝트...",
            "JavaScript, HTML, CSS 경험자 우대...",
            "Web App Project",
            "성춘향",
            "React",
            "Node.js",
            6,
            3
        ),
        Project(
            "Mobile Game Development",
            "모바일 게임 개발 프로젝트...",
            "Unity 사용 경험자 우대...",
            "Game Project",
            "김삿갓",
            "Unity",
            "C#",
            5,
            2
        ),
        Project(
            "Data Science Project",
            "데이터 과학 프로젝트...",
            "데이터 과학 관련 학위 소지자 우대\nPython, R 경험자 우대...",
            "Research Project",
            "박몽룡",
            "Python",
            "R",
            3,
            1
        ),
        Project(
            "Augmented Reality App",
            "증강현실 앱 개발 프로젝트...",
            "Unity, C# 경험자 우대...",
            "App Project",
            "김철수",
            "Unity",
            "C#",
            4,
            2
        ),
        Project(
            "Healthcare App",
            "헬스케어 앱 개발 프로젝트...",
            "React Native 경험자 우대...",
            "App Project",
            "이영희",
            "React Native",
            "Django",
            5,
            2
        ),
        Project(
            "Blockchain Project",
            "블록체인 프로젝트...",
            "블록체인 관련 경험자 우대...",
            "Blockchain Project",
            "최철호",
            "Ethereum",
            "Solidity",
            3,
            1
        ),
        Project(
            "Machine Learning Project",
            "머신러닝 프로젝트...",
            "머신러닝 관련 학위 소지자 우대\nPython, TensorFlow 경험자 우대...",
            "Research Project",
            "장보고",
            "Python",
            "TensorFlow",
            4,
            2
        ),
        Project(
            "IoT Development",
            "IoT 개발 프로젝트...",
            "IoT 개발 경험자 우대...",
            "IoT Project",
            "이이",
            "Python",
            "Node.js",
            6,
            3
        )
        // 기타 기술자 추가
    )
    SavedScreen()
}
