package com.mrx.quicknews.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrx.quicknews.ui.page.HomePage
import com.mrx.quicknews.ui.page.LocalPage
import com.mrx.quicknews.ui.page.NewsPage
import com.mrx.quicknews.util.Constant.NEWS_URL

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Local.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
    ) {
        composable(route = Screen.Home.route) {
            HomePage(navController)
        }
        composable(route = Screen.Local.route) {
            LocalPage(navController)
        }
        composable(route = Screen.DetailNews.route) {
            val url = navController.previousBackStackEntry?.savedStateHandle?.get<String>(NEWS_URL)
            NewsPage(navController, url.toString())
        }
    }
}