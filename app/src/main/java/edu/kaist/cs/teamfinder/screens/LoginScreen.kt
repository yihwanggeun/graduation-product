package edu.kaist.cs.teamfinder.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.SnackbarHostState
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
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import edu.kaist.cs.teamfinder.Globals
import edu.kaist.cs.teamfinder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRoute(onLoginSuccess: () -> Unit) {
    val navController = rememberNavController()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
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
            modifier = Modifier.padding(start = 16.dp, top = 40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        var email by remember {
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
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        var password by remember {
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
        val checkedState = remember { mutableStateOf(true) }
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
        //TODO:
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            Button(
                onClick = {
//                    loginViewModel.login(email, password)
                    val loginSuccess = true

                    if (loginSuccess) {
                        onLoginSuccess()
                    }
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
            GoogleSignInButton { isSuccess ->
                if (isSuccess) {

                } else {

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("You don't have an account yet?  ")
            }
            pushStringAnnotation(
                tag = "SIGNUP",
                annotation = "Sign up"
            ) // Use a tag to identify the clickable text
            withStyle(
                style = SpanStyle(
                    color = Color(0xFFFF9228),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Sign up")
            }
            pop()
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ClickableText(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans))
                ),
                onClick = {

                }
            )
        }
    }
}

@Composable
fun GoogleSignInButton(onSignInResult: (Boolean) -> Unit) {
    val context = LocalContext.current


    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, onSignInResult)
        }
    )
    Button(
        onClick = {
            signInLauncher.launch(googleSignInClient.signInIntent)
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

private fun handleSignInResult(task: Task<GoogleSignInAccount>, onSignInResult: (Boolean) -> Unit) {
    try {
        val account = task.getResult(ApiException::class.java)
        // 로그인이 성공했을 때 처리할 로직을 작성하세요.
        Globals.globalEmail = account.email.toString()
        Globals.globalUser = account.displayName.toString()
        onSignInResult(true)
        Log.d("GoogleSignInSuccess", account.email!!)
    } catch (e: ApiException) {
        // 로그인이 실패했을 때 처리할 로직을 작성하세요.
        onSignInResult(false)
        Log.d("GoogleSignInFailure", e.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // 가짜 Usecase와 ViewModel을 생성

    LoginRoute(onLoginSuccess = {})
}
