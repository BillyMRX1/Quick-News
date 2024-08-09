package com.mrx.quicknews.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_page")
    data object Local : Screen("local_page")
    data object DetailNews : Screen("detail_news_page")
}