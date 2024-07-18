package com.example.meliana_kusuma_pangkasidhi.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meliana_kusuma_pangkasidhi.model.User
import com.example.meliana_kusuma_pangkasidhi.route.NavigationItem
import com.example.meliana_kusuma_pangkasidhi.ui.theme.meliana_kusuma_pangkasidhiTheme
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal100
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal300
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal600
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal800
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal900
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.LoadingWidget
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.ProfileImage
import com.example.meliana_kusuma_pangkasidhi.viewmodel.DetailViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
) {
    var allowPrevScreenToRefresh by remember { mutableStateOf(false) }

    val username = navController.currentBackStackEntry?.arguments?.getString("username") ?: ""
    LaunchedEffect(Unit) {
        viewModel.getUserDetail(username)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            DetailTopBar(
                onBackPressed = {
                    onNavigateToPreviousScreen(navController, allowPrevScreenToRefresh)
                }
            )
        }
    ) { paddingContent ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingContent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = teal600)
                ) {
                    Spacer(modifier = Modifier.fillMaxSize())
                }
                Box(
                    modifier = Modifier.weight(1f)
                )
            }

            if (viewModel.isLoading) {
                LoadingWidget()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    DetailHeader(
                        user = viewModel.userState,
                        onFavoriteButtonClicked = {
                            allowPrevScreenToRefresh = true
                            viewModel.updateFavorite()
                        },
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    DetailBody(
                        user = viewModel.userState,
                        navController = navController
                    )
                }
            }

            if (viewModel.errorState) {
                showErrorToast(LocalContext.current, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        {},
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = teal600),
        navigationIcon = {
            IconButton(
                onClick = onBackPressed,
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Back handler icon"
                )
            }
        })
}

@Composable
private fun DetailHeader(
    user: User?,
    onFavoriteButtonClicked: () -> Unit,
) {
    val isFavoriteUser = user?.isFavorite == true

    ProfileImage(
        imageUrl = user?.avatarUrl ?: "",
        size = 140.dp,
    )
    Spacer(modifier = Modifier.size(8.dp))
    if (!user?.name.isNullOrEmpty()) {
        Text(
            text = user?.name ?: "",
            color = teal900,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
    Text(
        text = user?.username ?: "",
        color = teal800,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.size(4.dp))
    Button(
        onClick = onFavoriteButtonClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = teal300,
        )
    ) {
        Icon(
            if (isFavoriteUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            tint = teal600,
            contentDescription = "Favorite icon"
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = if (isFavoriteUser) "Remove from your favorite" else "Add to your favorite",
            color = teal600,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DetailBody(
    user: User?,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = teal100, shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        if (!user?.bio.isNullOrEmpty()) {
            Text(
                text = user?.bio ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                color = teal800,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(20f, TextUnitType.Sp)
            )
        }

        if (!user?.location.isNullOrEmpty()) {
            DetailItem(
                title = user?.location ?: "",
                icon = Icons.Default.LocationOn,
            )
        }

        if (!user?.email.isNullOrEmpty()) {
            DetailItem(
                title = user?.email ?: "",
                icon = Icons.Default.Email,
            )
        }

        if (!user?.company.isNullOrEmpty()) {
            DetailItem(
                title = user?.company ?: "",
                icon = Icons.Default.Home,
            )
        }

        DetailItem(
            Icons.Default.AccountCircle,
            "Followers: ${user?.followers ?: 0}",
        )
        DetailItem(
            Icons.Default.AccountCircle,
            "Following: ${user?.following ?: 0}",
        )
        DetailItem(
            Icons.Default.Menu,
            "Repository: ${user?.publicRepos ?: 0}",
        )
        GithubProfileDetailItem(
            onProfileClicked = {
                onNavigateToWebViewScreen(
                    username = user?.username ?: "",
                    navController = navController,
                )
            }
        )
    }
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
            contentDescription = "Item icon",
            tint = teal800,
        )

        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = teal800,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun GithubProfileDetailItem(
    onProfileClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onProfileClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
            contentDescription = "Item icon",
            tint = teal800,
        )

        Text(
            text = "Github Profile",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = teal800,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}

private fun onNavigateToWebViewScreen(username: String, navController: NavController) {
    val BASE_GITHUB_URL = "https://github.com/"
    val encodedUrl =
        URLEncoder.encode("$BASE_GITHUB_URL/${username}", StandardCharsets.UTF_8.toString())
    navController.navigate("${NavigationItem.WebViewScreen.route}/${encodedUrl}")
}

private fun onNavigateToPreviousScreen(
    navController: NavController,
    allowPrevScreenToRefresh: Boolean,
) {
    navController.previousBackStackEntry?.savedStateHandle?.set("refresh", allowPrevScreenToRefresh)
    navController.popBackStack()
}

private fun showErrorToast(
    context: Context,
    viewModel: DetailViewModel
) {
    Toast.makeText(context, "Something went wrong! Try again later.", Toast.LENGTH_LONG).show()
    viewModel.clearErrorState()
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    meliana_kusuma_pangkasidhiTheme {
        DetailScreen(navController)
    }
}