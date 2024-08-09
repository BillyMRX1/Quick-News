package com.mrx.quicknews.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mrx.quicknews.core.domain.model.Article

@Composable
fun NewsList(newsList: List<Article>, navController: NavHostController) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(newsList, key = { it.url }) { news ->
            NewsCard(news, navController)
        }
    }
}