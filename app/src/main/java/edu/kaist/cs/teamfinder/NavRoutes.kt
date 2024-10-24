package edu.kaist.cs.teamfinder

object NavRoutes {
    val Home = Screen("home")
    val Feed = Screen("feed")
    val Add = Screen("add")
    val Chat = Screen("chat")
    val Saved = Screen("saved")
    val Apply = Screen("apply")  // 새로운 화면 라우트 추가

    class Screen(val route: String) {
        // You can define additional arguments here for passing arguments between screens
        // 예를 들어 아래와 같이 정의할 수 있습니다.

        // 게시글의 ID를 argument로 받는 Details 화면의 경우:
        // val Details = Screen("details/{postId}")
        // fun Details.withArgs(postId: String): String = "details/$postId"
    }
}