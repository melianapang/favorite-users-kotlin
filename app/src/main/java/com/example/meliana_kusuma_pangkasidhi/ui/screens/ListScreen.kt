package com.example.meliana_kusuma_pangkasidhi.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal200
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal300
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal400
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal600
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal800
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.LoadingWidget
import com.example.meliana_kusuma_pangkasidhi.ui.widgets.UserCard
import com.example.meliana_kusuma_pangkasidhi.viewmodel.ListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel<ListViewModel>()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0) { 2 }
    val tabTitles = listOf("POPULAR", "YOUR FAVORITES")

    initData(viewModel, navController)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = teal300),
        contentColor = teal300,
        topBar = {
            DefaultTopBar(
                title = "Let's explore more!",
                icon = Icons.Default.Search,
                onIconClicked = {
                    navController.navigate(NavigationItem.SearchScreen.route)
                },
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.height(64.dp),
                containerColor = teal300,
                indicator = {},
                divider = {},
                tabs = {
                    tabTitles.forEachIndexed { index, title ->
                        UserTab(
                            title = title,
                            isSelected = pagerState.currentPage == index,
                            onTabClicked = {
                                if (pagerState.currentPage == index) return@UserTab
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = teal300),
            ) { page ->
                if (page == 0) {
                    UserListView(
                        viewModel = viewModel,
                        navController = navController
                    )
                } else {
                    FavoriteUserListView(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }

        if (viewModel.errorState) {
            showErrorToast(LocalContext.current, viewModel)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultTopBar(
    title: String,
    icon: ImageVector? = null,
    onIconClicked: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = teal600,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = teal300,
            scrolledContainerColor = teal200,
            navigationIconContentColor = teal600,
            titleContentColor = teal600,
            actionIconContentColor = teal600,
        ),
        actions = {
            if (icon == null) return@TopAppBar

            IconButton(
                onClick = onIconClicked,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Search icon",
                    tint = teal600,
                )
            }
        })
}

@Composable
private fun UserTab(title: String, isSelected: Boolean, onTabClicked: () -> Unit) {
    Tab(selected = isSelected,
        onClick = onTabClicked,
        text = {
            Text(
                text = title,
                color = teal600,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        },
        icon = {
            if (!isSelected) return@Tab
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(color = teal600, shape = CircleShape)
            )
        }
    )
}

@Composable
private fun UserListView(
    viewModel: ListViewModel,
    navController: NavController
) {
    val data = viewModel.usersState.value?.collectAsLazyPagingItems()
    when {
        data?.loadState?.refresh is LoadState.Error ||
                data?.loadState?.append is LoadState.Error -> {
            showErrorToast(LocalContext.current)
        }

        data?.loadState?.prepend is LoadState.Loading -> {
            LoadingWidget()
        }
    }

    if (data?.loadState?.refresh !is LoadState.NotLoading) return
    val scrollState = rememberLazyListState()
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
                    username = it.username,
                    avatarUrl = it.avatarUrl,
                    navigateToDetailScreen = {
                        navController.navigate("${NavigationItem.DetailScreen.route}/${it.username}")
                    })
            }
        }
    }
}

@Composable
private fun FavoriteUserListView(
    viewModel: ListViewModel,
    navController: NavController,
) {
    val data = viewModel.favoriteUsersState ?: listOf()
    if (data.isEmpty()) {
        EmptyState()
        return
    }

    val scrollState = rememberLazyListState()
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
        items(data.size) { index ->
            val currentData = data.get(index)
            UserCard(
                username = currentData.username,
                avatarUrl = currentData.avatarUrl,
                navigateToDetailScreen = {
                    navController.navigate("${NavigationItem.DetailScreen.route}/${currentData.username}")
                }
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "You don't have favorite list yet",
            color = teal800,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            "Go to POPULAR list to favorite a user",
            color = teal400,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

private fun initData(
    viewModel: ListViewModel,
    navController: NavController
) {
    val isRefreshEligible = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("refresh")

    if (isRefreshEligible == true) {
        viewModel.getFavoriteUsers()
        navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("refresh")
    }
}

private fun showErrorToast(
    context: Context,
    viewModel: ListViewModel? = null,
) {
    Toast.makeText(context, "Something went wrong! Try again later.", Toast.LENGTH_LONG).show()
    viewModel?.clearErrorState()
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    val navController = rememberNavController()
    meliana_kusuma_pangkasidhiTheme {
        ListScreen(navController)
    }
}