package com.example.meliana_kusuma_pangkasidhi.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.meliana_kusuma_pangkasidhi.route.NavigationItem
import com.example.meliana_kusuma_pangkasidhi.ui.theme.meliana_kusuma_pangkasidhiTheme
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal400
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal600
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal800
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.LoadingWidget
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.UserCard
import com.example.meliana_kusuma_pangkasidhi.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel<SearchViewModel>()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            SearchBarWidget(
                viewModel = viewModel,
                onBackPressed = {
                    onNavigateToPreviousScreen(navController)
                },
                onSearchClicked = {
                    viewModel.searchUsers(it)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SearchHistoryListView(
                keywords = viewModel.searchHistoryState?.historyData,
                onHistoryItemClicked = { keyword ->
                    viewModel.searchUsers(keyword)
                }
            )

            if (viewModel.isLoading) {
                LoadingWidget()
            } else {
                UserListView(
                    viewModel,
                    navController
                )
            }

            if (viewModel.errorState) {
                showErrorToast(LocalContext.current, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBarWidget(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val keywordViewModel = viewModel.searchHistoryState?.currentKeyword ?: ""
    var keyword by remember(key1 = keywordViewModel) { mutableStateOf(keywordViewModel) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
                .width(32.dp)
                .wrapContentHeight()
                .padding(start = 8.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back handler icon",
                tint = teal600,
            )
        }
        TextField(
            value = keyword,
            onValueChange = { key ->
                keyword = key
            },
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(end = 8.dp),
            placeholder = {
                Text(
                    "Look for what you want here ...",
                    color = teal400,
                    textAlign = TextAlign.Start
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = teal600,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedTextColor = Color.DarkGray,
                focusedTextColor = Color.DarkGray,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked.invoke(keyword)
                keyboardController?.hide()
            })
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SearchHistoryListView(
    keywords: List<String>?,
    onHistoryItemClicked: (String) -> Unit,
) {
    if (keywords.isNullOrEmpty()) return

    Text(
        text = "Your history",
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 16.dp
        ),
        color = teal400,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start
    )
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp
            ),
    ) {
        keywords.forEach { item ->
            SearchItem(
                keyword = item,
                onClicked = onHistoryItemClicked
            )
        }
    }
}

@Composable
private fun SearchItem(
    keyword: String,
    onClicked: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(1.dp, teal400, RoundedCornerShape(30))
            .clickable {
                onClicked(keyword)
            },
    ) {
        Text(
            text = keyword,
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
            color = teal600,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun UserListView(
    viewModel: SearchViewModel,
    navController: NavController
) {
    val data = viewModel.usersState.value?.collectAsLazyPagingItems()
    when {
        data?.loadState?.refresh is LoadState.Error ||
                data?.loadState?.append is LoadState.Error -> {
            showErrorToast(LocalContext.current)
        }

        data?.loadState?.refresh is LoadState.Loading -> {
            LoadingWidget()
        }
    }

    if (data?.loadState?.refresh !is LoadState.NotLoading) return
    val scrollState = rememberLazyListState()

    if (data.itemCount == 0) {
        EmptyState()
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
            ),
        state = scrollState,
        contentPadding = PaddingValues(20.dp)
    ) {
        items(data.itemCount) { index ->
            data.get(index)?.let {
                UserCard(
                    username = it.login,
                    avatarUrl = it.avatarUrl,
                    navigateToDetailScreen = {
                        navController.navigate("${NavigationItem.DetailScreen.route}/${it.login}")
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Sorry",
            color = teal800,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            "We couldn't find any the users you're looking for",
            color = teal600,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

private fun onNavigateToPreviousScreen(
    navController: NavController
) {
    val isRefreshEligible = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("refresh")
    navController.apply {
        this.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("refresh")
        this.previousBackStackEntry?.savedStateHandle?.set("refresh", isRefreshEligible)
        this.popBackStack()
    }
}

private fun showErrorToast(context: Context, viewModel: SearchViewModel? = null) {
    Toast.makeText(context, "Something went wrong! Try again later.", Toast.LENGTH_LONG).show()
    viewModel?.clearErrorState()
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val navController = rememberNavController()
    meliana_kusuma_pangkasidhiTheme {
        SearchScreen(navController)
    }
}