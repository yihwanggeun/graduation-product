package edu.kaist.cs.teamfinder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import edu.kaist.cs.teamfinder.screens.HomeScreen
import edu.kaist.cs.teamfinder.ui.theme.TeamFinderTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


lateinit var mGoogleSignInClient: GoogleSignInClient
lateinit var resultLauncher : ActivityResultLauncher<Intent>
var gson = GsonBuilder().setLenient().create()
val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.0.2:8080/") // API의 베이스 URL을 설정합니다
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
    .build()

val apiService = retrofit.create(ApiService::class.java)
class LoginActivity : ComponentActivity() {
    private companion object {
        private const val RC_SIGN_IN = 1001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        println(R.drawable.sample2)
        println(R.drawable.sample3)
        super.onCreate(savedInstanceState)
        setResultSignUp()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        setContent {
            TeamFinderTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute.Login.name
                    ) {
                        composable(route = LoginRoute.Login.name) {
                            Login(name = "", navController = navController)
                        }
                        composable(route = LoginRoute.TestLogin.name) {
                            TestLogin(navController = navController)
                        }
                        composable(route = LoginRoute.CreateAccount.name) {
                            CreateAccount(navController = navController)
                        }
                        composable(route = LoginRoute.Home.name) {
                            HomeScreen()
                        }
                        composable(route = LoginRoute.MainScreen.name) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
    fun setResultSignUp(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()
            val photoUrl = account?.photoUrl.toString()
            println(account)
        } catch (e: ApiException){
            Log.w("failed",e.statusCode.toString())
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestLogin(navController: NavHostController) {
    val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Welcome Back",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF0D0140),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing\nelit, sed do eiusmod tempor",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 19.2.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF524B6B),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF0D0140),
                ),
                modifier = Modifier.padding(start = 16.dp,top = 40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            var email by remember{
                mutableStateOf("")
            }
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF0D0140),
                ),
                modifier = Modifier.padding(start = 16.dp,top = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            var password by remember{
                mutableStateOf("")
            }
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
            val checkedState = remember {mutableStateOf(true)}
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.padding(start = 24.dp),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.5.dp, color = Color(0xFFE6E1FF))
                ) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(if (checkedState.value) Color(0xFFE6E1FF) else Color.White)
                            .clickable {
                                checkedState.value = !checkedState.value
                            },
                    ) {
                        if (checkedState.value)
                            Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
                    }
                }


                Text(
                    text = "Remember me",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFAAA6B9),
                    ),
                    modifier = Modifier.padding(start = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            //val globalUser = remember { mutableStateOf("") }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Button(
                    onClick = {
                        val userData : User = User(email,password,null,null)
                        val DataList = ArrayList<User>()
                        apiService.checkUser(email, password).enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.isSuccessful) {
                                    val json = response.body()?.string()

                                    try {
                                        // Parse the response as UserResponse
                                        val userResponse = Gson().fromJson(json, UserResponse::class.java)

                                        // Check if results are not empty and retrieve the first user
                                        val user = userResponse.results.firstOrNull()
                                        if (user?.fullname != null) {
                                            Globals.globalUser = user.fullname
                                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                                            navController.navigate(LoginRoute.MainScreen.name)
                                        } else {
                                            Toast.makeText(context, "Login failed: User data is incomplete", Toast.LENGTH_LONG).show()
                                        }
                                    } catch (e: JsonSyntaxException) {
                                        e.printStackTrace()
                                        Toast.makeText(context, "Failed to parse login response", Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    val errorMessage = when (response.code()) {
                                        401 -> "Invalid email or password"
                                        500 -> "Server error"
                                        else -> "Login failed: ${response.message()}"
                                    }
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                // 통신 실패 처리
                                println("Fail")
                            }
                        })

                    },
                    shape = RoundedCornerShape(size = 6.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF130160)),
                    modifier = Modifier
                        .width(266.dp)
                        .height(50.dp)
                        .shadow(
                            elevation = 62.dp,
                            spotColor = Color(0x2E99ABC6),
                            ambientColor = Color(0x2E99ABC6)
                        )


                ) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Button(
                    onClick = {
                        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
                        resultLauncher.launch(signInIntent)
                        val account = GoogleSignIn.getLastSignedInAccount(context)
                        val email = account?.email.toString()
                        val familyName = account?.familyName.toString()
                        val givenName = account?.givenName.toString()
                        val displayName = account?.displayName.toString()
                        val photoUrl = account?.photoUrl.toString()
                        val password = "test"
                        val userData : User = User(email,password,displayName,photoUrl)
                        println("Here?")
                        println(userData)
                        navController.navigate(LoginRoute.CreateAccount.name)
                        apiService.createUser(userData).enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                println(response)
                                if (response.isSuccessful) {
                                    // User successfully created on the server
                                    val createdUser: String? = response.body()
                                    println("SUCCESS")
                                    println(response.body())
                                    navController.navigate(LoginRoute.MainScreen.name)
                                    mGoogleSignInClient.signOut()

                                    // Handle the created user object as needed
                                } else {
                                    // Error occurred while creating the user
                                    // Handle the error case
                                    println("FAil")
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                // Network or API call failure occurred
                                // Handle the failure case
                                println("API request failed: ${t.message}")
                            }
                        })

                          },
                    shape = RoundedCornerShape(size = 6.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFD6CDFE)),
                    modifier = Modifier
                        .width(266.dp)
                        .height(50.dp)
                        .shadow(
                            elevation = 62.dp,
                            spotColor = Color(0x2E99ABC6),
                            ambientColor = Color(0x2E99ABC6)
                        )


                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google logo",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            fontSize = 14.sp,
                            text = "SIGN IN WITH GOOGLE",
                            color = Color(0xFF130160),
                            letterSpacing = 0.84.sp,
                        )

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            val text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("You don't have an account yet?  ")
                }
                pushStringAnnotation(tag = "SIGNUP", annotation = "Sign up") // Use a tag to identify the clickable text
                withStyle(style = SpanStyle(color = Color(0xFFFF9228), textDecoration = TextDecoration.Underline)) {
                    append("Sign up")
                }
                pop()
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ClickableText(
                    text = text,
                    style = TextStyle(fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.dmsans))),
                    onClick = { offset ->
                        text.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                            .firstOrNull()?.let {
                                navController.navigate(LoginRoute.CreateAccount.name)
                            }
                    }
                )
            }


        }


}

@Composable
fun Login(name: String, modifier: Modifier = Modifier, navController: NavHostController) {
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp), horizontalArrangement = Arrangement.Center) {
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
                        withStyle(style = SpanStyle(color = Color(0xFFFCA34D), textDecoration = TextDecoration.Underline)) {
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
                    text = "Explore all the most exciting job roles based\non your interest and study major.",
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
                val coroutineScope = rememberCoroutineScope()

                Image(
                    painter = painterResource(id = R.drawable.login2),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(0.dp)
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {

                            navController.navigate(LoginRoute.TestLogin.name)
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(modifier: Modifier = Modifier, navController: NavHostController){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Create an Account",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0D0140),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing\nelit, sed do eiusmod tempor",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 19.2.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight(400),
                color = Color(0xFF524B6B),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Fullname",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0D0140),
            ),
            modifier = Modifier.padding(start = 16.dp,top = 40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        var fullname by remember{
            mutableStateOf("")
        }
        TextField(
            value = fullname,
            onValueChange = { fullname = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
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
        Text(
            text = "Email",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0D0140),
            ),
            modifier = Modifier.padding(start = 16.dp,top = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        var new_email by remember{
            mutableStateOf("")
        }
        TextField(
            value = new_email,
            onValueChange = { new_email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
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
        Text(
            text = "password",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0D0140),
            ),
            modifier = Modifier.padding(start = 16.dp,top = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        var password by remember{
            mutableStateOf("")
        }
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
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
        val checkedState = remember {mutableStateOf(true)}
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                modifier = Modifier.padding(start = 24.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.5.dp, color = Color(0xFFE6E1FF))
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(if (checkedState.value) Color(0xFFE6E1FF) else Color.White)
                        .clickable {
                            checkedState.value = !checkedState.value
                        },
                ) {
                    if (checkedState.value)
                        Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
                }
            }


            Text(
                text = "Remember me",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFAAA6B9),
                ),
                modifier = Modifier.padding(start = 8.dp)

            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            Button(
                onClick = {
                    val email = new_email
                    val familyName = null
                    val givenName = null
                    val displayName = fullname
                    val photoUrl = null
                    val password = password
                    val userData : User = User(email,password, displayName,photoUrl)
                    //navController.navigate(LoginScreen.CreateAccount.name)
                    apiService.createUser(userData).enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                // User successfully created on the server
                                val createdUser: String? = response.body()
                                // Handle the created user object as needed
                                println(response.body())
                            } else {
                                // Error occurred while creating the user
                                // Handle the error case
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            // Network or API call failure occurred
                            // Handle the failure case
                        }
                    })

                },
                shape = RoundedCornerShape(size = 6.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF130160)),
                modifier = Modifier
                    .width(266.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 62.dp,
                        spotColor = Color(0x2E99ABC6),
                        ambientColor = Color(0x2E99ABC6)
                    )


            ) {
                Text(
                    text = "Sign up",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.84.sp,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            Button(
                onClick = {},
                shape = RoundedCornerShape(size = 6.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFD6CDFE)),
                modifier = Modifier
                    .width(266.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 62.dp,
                        spotColor = Color(0x2E99ABC6),
                        ambientColor = Color(0x2E99ABC6)
                    )


            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google logo",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        fontSize = 14.sp,
                        text = "SIGN IN WITH GOOGLE",
                        color = Color(0xFF130160),
                        letterSpacing = 0.84.sp,
                    )

                }
            }
        }

        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("You already have an account? ")
            }
            pushStringAnnotation(tag = "SIGNIN", annotation = "Sign In") // Use a tag to identify the clickable text
            withStyle(style = SpanStyle(color = Color(0xFFFF9228), textDecoration = TextDecoration.Underline)) {
                append("Sign In")
            }
            pop()
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), contentAlignment = Alignment.Center) {
            ClickableText(
                text = text,
                style = TextStyle(fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.dmsans))),
                onClick = { offset ->
                    text.getStringAnnotations(tag = "SIGNIN", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate(LoginRoute.TestLogin.name)
                            println("Sign In clicked")
                        }
                }
            )
        }



    }
}
/*
fun googlelogin(){
    val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
    resultLauncher.launch(signInIntent)
}


@Composable
fun googlelogout(){
    mGoogleSignInClient.signOut()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // 로그아웃 성공
                println("Google sign out successful")
            } else {
                // 로그아웃 실패
                println("Google sign out failed")
            }
        }
}
fun posttest(){

}
@Composable
fun GetCurrentUserProfile(context: Context) {
    val curUser = GoogleSignIn.getLastSignedInAccount(context)
    println("testest")
    println(curUser)
    curUser?.let {
        val email = curUser.email.toString()
        val familyName = curUser.familyName.toString()
        val givenName = curUser.givenName.toString()
        val displayName = curUser.displayName.toString()
        val photoUrl = curUser.photoUrl.toString()

        Log.d("UserProfile", "Email: $email")
        Log.d("UserProfile", "Family Name: $familyName")
        Log.d("UserProfile", "Given Name: $givenName")
        Log.d("UserProfile", "Display Name: $displayName")
        Log.d("UserProfile", "Photo URL: $photoUrl")
    }
    println(curUser)
}

fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
    try {
        Log.d("Tag","Handle")
        val account = completedTask.getResult(ApiException::class.java)
        val email = account?.email.toString()
        val familyName = account?.familyName.toString()
        val givenName = account?.givenName.toString()
        val displayName = account?.displayName.toString()
        val photoUrl = account?.photoUrl.toString()
        Log.d("UserProfile", "Email: $email")
        Log.d("UserProfile", "Family Name: $familyName")
        Log.d("UserProfile", "Given Name: $givenName")
        Log.d("UserProfile", "Display Name: $displayName")
        Log.d("UserProfile", "Photo URL: $photoUrl")
    } catch (e: ApiException){
        Log.w("failed",e.statusCode.toString())
    }

}
fun fetchMessageFromServer() {
    val call = apiService.getHelloKaist()
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                val helloKaist = response.body()
                println(message = helloKaist) // "Hello Kaist"를 출력합니다
            } else {
                // 응답이 실패한 경우에 대한 처리를 수행합니다
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            // 통신 실패 처리를 수행합니다
        }
    })

}
*/


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    TeamFinderTheme {
        val navController = rememberNavController()
        Login(name = "Kaist", navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestLoginScreen() {
    TeamFinderTheme {
        val navController = rememberNavController()
        TestLogin(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestCreateAccountScreen() {
    TeamFinderTheme {
        val navController = rememberNavController()
        CreateAccount(navController = navController)
    }
}


